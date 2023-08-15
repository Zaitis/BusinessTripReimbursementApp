import com.sun.net.httpserver.HttpServer;
import controller.AdminController;
import controller.EndUserController;
import database.H2config;
import model.Receipt;
import org.h2.tools.Server;

import java.math.BigDecimal;
import java.net.InetSocketAddress;

import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        new H2config();
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/admin", new AdminController());
        server.createContext("/enduser", new EndUserController());

        server.setExecutor(null);
        server.start();

    }
}