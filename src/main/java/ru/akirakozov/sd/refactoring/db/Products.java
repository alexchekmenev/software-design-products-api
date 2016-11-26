package ru.akirakozov.sd.refactoring.db;

import ru.akirakozov.sd.refactoring.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by creed on 26.11.16.
 */
public class Products {

    public static void initialize() throws SQLException {
        try (DbConnection connection = new DbConnection()) {
            String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " PRICE          INT     NOT NULL)";
            Connection c = connection.getConnection();
            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }
    }

    public static void add(DbConnection connection, Product product) throws SQLException {
        assert (connection != null);
        assert (product != null);
        Connection c = connection.getConnection();
        String sql = "INSERT INTO PRODUCT (NAME, PRICE) VALUES (\"" +
                product.getName() + "\"," + product.getPrice() + ")";
        Statement stmt = c.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
    }

    public static List<Product> get(DbConnection connection) throws SQLException {
        assert (connection != null);
        List<Product> products = new ArrayList<>();
        Connection c = connection.getConnection();
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT");
        while (rs.next()) {
            products.add(new Product(rs.getString("name"), rs.getLong("price")));
        }
        rs.close();
        stmt.close();
        return products;
    }

    public static List<Product> getOrderedByPrice(DbConnection connection, int order, int limit) throws SQLException {
        assert (connection != null);
        assert (limit > 0);
        assert (order == -1 || order == 1);
        Connection c = connection.getConnection();
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE " +
                (order == -1 ? "DESC" : "ASC")+" LIMIT "+limit);
        List<Product> products = new ArrayList<>();
        while (rs.next()) {
            products.add(new Product(rs.getString("name"), rs.getLong("price")));
        }
        rs.close();
        stmt.close();
        return products;
    }

    public static long sum(DbConnection connection) throws SQLException {
        assert (connection != null);
        Connection c = connection.getConnection();
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT SUM(price) FROM PRODUCT");
        long result = rs.getLong(1);
        rs.close();
        stmt.close();
        return result;
    }

    public static int count(DbConnection connection) throws SQLException {
        assert (connection != null);
        Connection c = connection.getConnection();
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM PRODUCT");
        int result = rs.getInt(1);
        rs.close();
        stmt.close();
        return result;
    }


}
