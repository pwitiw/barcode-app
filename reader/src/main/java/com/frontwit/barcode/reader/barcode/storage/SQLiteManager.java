package com.frontwit.barcode.reader.barcode.storage;

import com.frontwit.barcode.reader.barcode.BarcodeCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;

@Component
public class SQLiteManager {

    private static final Logger LOGGER = Logger.getLogger(SQLiteManager.class.getName());
    private static final String TABLE_NAME = "barcode";
    private static final String ID = "id";
    private static final String BARCODE = "barcode";
    private static final String READER_ID = "reader_id";
    private static final String DATE = "date";

    @Value("${sqlite-url}")
    String url;

    private Connection connection;

    @PostConstruct
    private void initDb() throws SQLException {
        connection = DriverManager.getConnection(url);
        LOGGER.info("Connection to SQLite has been established.");
        createTableIfNotExists();
    }

    boolean persist(Integer readerId, Long barcode) {
        String sql = format("INSERT INTO %s (%s, %s, %s) VALUES(?,?,?)", TABLE_NAME, READER_ID, BARCODE, DATE);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, readerId);
            statement.setLong(2, barcode);
            statement.setString(3, LocalDateTime.now().toString());
            return statement.execute();
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
        }
        return false;
    }

    List<BarcodeCommand> findAll() {
        String sql = format("SELECT * FROM %s", TABLE_NAME);
        List<BarcodeCommand> results = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int readerId = resultSet.getInt(READER_ID);
                long barcode = resultSet.getLong(BARCODE);
                long id = resultSet.getLong(ID);
                LocalDateTime date = LocalDateTime.parse(resultSet.getString(DATE));
                results.add(new BarcodeCommand(id, readerId, barcode, date));
            }
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
        }

        return results;
    }

    void delete(Collection<Long> ids) {
        String values = ids
                .stream()
                .map(String::valueOf)
                .reduce((arg1, arg2) -> arg1 + ", " + arg2)
                .orElse("");
        String sql = format("DELETE FROM %s WHERE id IN (%s)", TABLE_NAME, values);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
            LOGGER.info(format("Deleted entries with ids: [%s]", values));
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
        }
    }

    private void createTableIfNotExists() throws SQLException {
        final String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(\n"
                + ID + "integer PRIMARY KEY,\n"
                + READER_ID + "	integer NOT NULL,\n"
                + BARCODE + "	integer NOT NULL,\n"
                + DATE + "  	text\n"
                + ");";
        boolean result = connection.prepareStatement(sql).execute();
        LOGGER.info(format("Creation script ended with result: %s", result));
    }

    @PreDestroy
    private void tearDown() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.warning(e.getMessage());
            }
        }
    }
}
