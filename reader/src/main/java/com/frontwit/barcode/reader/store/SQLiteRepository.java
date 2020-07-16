package com.frontwit.barcode.reader.store;

import com.frontwit.barcode.reader.application.ProcessFrontCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.lang.String.format;

@SuppressWarnings("MultipleStringLiterals")
public class SQLiteRepository {
    private static final Logger LOG = LoggerFactory.getLogger(SQLiteRepository.class.getName());
    private static final String TABLE_NAME = "barcode";
    private static final String ID = "id";
    private static final String BARCODE = "barcode";
    private static final String READER_ID = "reader_id";
    private static final String DATE = "date";

    @Value("${sqlite-url}")
    private String url;

    private Connection connection;

    @PostConstruct
    void initDb() throws SQLException {
        connection = DriverManager.getConnection(url);
        LOG.info("[SQLite] Connection to SQLite has been established.");
        createTableIfNotExists();
    }

    boolean persist(Integer readerId, Long barcode, LocalDateTime dateTime) {
        String sql = "INSERT INTO " + TABLE_NAME + " (" + READER_ID + ", " + BARCODE + ", " + DATE + ", date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, UUID.randomUUID().toString());
            statement.setLong(2, barcode);
            statement.setInt(3, readerId);
            statement.setString(4, dateTime.toString());
            return statement.execute();
        } catch (SQLException e) {
            LOG.warn("Insertion into database failed", e);
        }
        return false;
    }

    Map<UUID, ProcessFrontCommand> findAll() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        var results = new HashMap<UUID, ProcessFrontCommand>();
        try (var statement = connection.prepareStatement(sql)) {
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                var uuid = UUID.fromString(resultSet.getString(ID));
                results.put(uuid, mapBarcode(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            LOG.warn("Failed during database command execution", e);
        }

        return results;
    }

    void delete(Collection<UUID> ids) {
        String values = ids.stream()
                .map(id -> "'" + id + "'")
                .reduce((id1, id2) -> id1 + "," + id2)
                .orElse("");
        String sql = format("DELETE FROM " + TABLE_NAME + " WHERE id IN (%s)", values);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
            LOG.info(format("[SQLite] Deleted entries with ids: [%s]", values));
        } catch (SQLException e) {
            LOG.warn("Failed during clearing database", e);
        }
    }

    @SuppressWarnings("FileTabCharacter")
    private void createTableIfNotExists() {
        final var sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "( "
                + ID + " text PRIMARY KEY, "
                + READER_ID + "	integer NOT NULL, "
                + BARCODE + " integer NOT NULL, "
                + DATE + " text "
                + ");";
        try (var statement = connection.prepareStatement(sql)) {
            var result = statement.execute();
            LOG.info(format("[SQLite] Creation script ended with result: %s", result));
        } catch (SQLException e) {
            LOG.warn("Failed during db schema creation");
        }
    }

    ProcessFrontCommand mapBarcode(ResultSet resultSet) throws SQLException {
        var readerId = resultSet.getInt(READER_ID);
        var barcode = resultSet.getLong(BARCODE);
        var dateTime = LocalDateTime.parse(resultSet.getString(DATE));
        return new ProcessFrontCommand(readerId, barcode, dateTime);
    }

    @PreDestroy
    void tearDown() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.warn("Failed during closing db", e);
            }
        }
    }
}
