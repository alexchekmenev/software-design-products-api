package ru.akirakozov.sd.refactoring.servlet;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import ru.akirakozov.sd.refactoring.ResponseDecorator;
import ru.akirakozov.sd.refactoring.db.DbConnection;
import ru.akirakozov.sd.refactoring.db.Products;
import ru.akirakozov.sd.refactoring.model.Product;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author akirakozov
 */
public class ProductsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));
        try (DbConnection connection = new DbConnection()) {
            Product product = new Product(name, price);
            if (product.isValid()) {
                Products.add(connection, product);
                ResponseDecorator.responseNoContent(response);
            } else {
                ResponseDecorator.responseBadRequest(response, "Invalid name or price");
            }
        } catch (SQLException e) {
            ResponseDecorator.responseServerError(response, e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (DbConnection connection = new DbConnection()) {
            List<Product> products = Products.get(connection);
            final JsonObject result = new JsonObject();
            final JsonArray array = new JsonArray();
            products.forEach(product -> array.add(product.toJson()));
            result.addProperty("count", products.size());
            result.add("data", array);
            ResponseDecorator.responseJson(response, result);
        } catch (SQLException e) {
            ResponseDecorator.responseServerError(response, e.getMessage());
        }
    }
}
