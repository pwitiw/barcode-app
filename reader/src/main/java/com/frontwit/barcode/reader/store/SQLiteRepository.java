package com.frontwit.barcode.reader.store;

import com.frontwit.barcode.reader.application.ProcessFrontCommand;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import static java.lang.String.format;

public class SQLiteRepository {

    private static final Logger LOGGER = Logger.getLogger(SQLiteRepository.class.getName());
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
        LOGGER.info("[SQLite] Connection to SQLite has been established.");
        createTableIfNotExists();
    }

    boolean persist(Integer readerId, Long barcode, LocalDateTime dateTime) {
        String sql = "INSERT INTO barcode (id, barcode, reader_id, date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, UUID.randomUUID().toString());
            statement.setLong(2, barcode);
            statement.setInt(3, readerId);
            statement.setString(4, dateTime.toString());
            return statement.execute();
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
        }
        return false;
    }

    Map<UUID, ProcessFrontCommand> findAll() {
        String sql = "SELECT * FROM barcode";
        var results = new HashMap<UUID, ProcessFrontCommand>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                var uuid = UUID.fromString(resultSet.getString(ID));
                results.put(uuid, mapBarcode(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
        }

        return results;
    }

    void delete(Collection<UUID> ids) {
        String values = ids.stream()
                .map(id -> "'" + id + "'")
                .reduce((id1, id2) -> id1 + "," + id2)
                .orElse("");
        String sql = format("DELETE FROM barcode WHERE id IN (%s)", values);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
            LOGGER.info(format("[SQLite] Deleted entries with ids: [%s]", values));
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
        }

    }

    private void createTableIfNotExists() throws SQLException {
        final var sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(\n"
                + ID + " text PRIMARY KEY,\n"
                + READER_ID + "	integer NOT NULL,\n"
                + BARCODE + "	integer NOT NULL,\n"
                + DATE + "  	text\n"
                + ");";
        var result = connection.prepareStatement(sql).execute();
        LOGGER.info(format("[SQLite] Creation script ended with result: %s", result));
    }

    ProcessFrontCommand mapBarcode(ResultSet resultSet) throws SQLException {
        var readerId = resultSet.getInt(READER_ID);
        var barcode = resultSet.getLong(BARCODE);
        var dateTime = LocalDateTime.parse(resultSet.getString(DATE));
        return new ProcessFrontCommand(readerId, barcode, dateTime);
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
