package ru.akirakozov.sd.refactoring;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.akirakozov.sd.refactoring.db.Products;
import ru.akirakozov.sd.refactoring.servlet.ProductsServlet;
import ru.akirakozov.sd.refactoring.servlet.CommandsServlet;

/**
 * @author akirakozov
 */
public class Main {
    public static void main(String[] args) throws Exception {

        Products.initialize();

        Server server = new Server(9000);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new ProductsServlet()), "/products");
        context.addServlet(new ServletHolder(new CommandsServlet()), "/commands");

        server.start();
        server.join();
    }
}
