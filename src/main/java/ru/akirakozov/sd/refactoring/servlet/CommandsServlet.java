package ru.akirakozov.sd.refactoring.servlet;

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

/**
 * @author akirakozov
 */
public class CommandsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");
        try (DbConnection connection = new DbConnection()) {
            switch (command) {
                case "max":
                    List<Product> descProducts = Products.getOrderedByPrice(connection, -1, 1);
                    ResponseDecorator.responseJson(response, descProducts.get(0).toJson());
                    break;
                case "min":
                    List<Product> ascProducts = Products.getOrderedByPrice(connection, 1, 1);
                    ResponseDecorator.responseJson(response, ascProducts.get(0).toJson());
                    break;
                case "sum":
                    JsonObject sum = new JsonObject();
                    sum.addProperty("result", Products.sum(connection));
                    ResponseDecorator.responseJson(response, sum);
                    break;
                case "count":
                    JsonObject count = new JsonObject();
                    count.addProperty("result", Products.count(connection));
                    ResponseDecorator.responseJson(response, count);
                    break;
                default:
                    ResponseDecorator.responseBadRequest(response, "Unknown command: " + command);
            }
        } catch (SQLException e) {
            ResponseDecorator.responseServerError(response, e.getMessage());
        }
    }

}
