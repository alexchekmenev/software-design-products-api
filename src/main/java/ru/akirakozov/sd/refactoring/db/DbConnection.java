package ru.akirakozov.sd.refactoring.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by creed on 26.11.16.
 */
public class DbConnection implements AutoCloseable {
    private Connection connection;

    public DbConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:test.db");
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
