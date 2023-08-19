
import com.sun.net.httpserver.HttpServer;
import config.AdminConfig;
import config.RateConfig;
import controller.AdminController;
import controller.EndUserController;
import database.H2config;
import util.AdminAuthenticator;

import java.net.InetSocketAddress;


public class Main {
    public static void main(String[] args) throws Exception {
        new H2config();
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/admin", (exchange) -> {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "http://localhost:3000");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, GET, OPTIONS");

            if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }
            AdminController adminController = new AdminController(
                    new AdminAuthenticator(new AdminConfig()), RateConfig.getInstance()

            );

            String path = exchange.getRequestURI().getPath();
            String response = "";

            if ("/admin/login".equals(path)) {
                response = adminController.handleLogin(exchange);
            } else if ("/admin/updateRates".equals(path)) {
                response = adminController.handleUpdateRates(exchange);
            }else if ("/admin/getRates".equals(path)) {
                response = adminController.handleGetRates();
            }

            adminController.sendResponse(exchange, 200, response);
        });

        server.createContext("/enduser", (exchange) -> {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "http://localhost:3000");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, GET, OPTIONS");

            if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            EndUserController endUserController = new EndUserController();

            String path = exchange.getRequestURI().getPath();
            String response = "";

            if ("/enduser/createReimbursement".equals(path)) {
                response = endUserController.handleCreateReimbursement(exchange);
            }else if ("/enduser/displayReimbursements".equals(path)) {
                response = endUserController.handleDisplayReimbursements();
            }else if ("/enduser/getTypes".equals(path)) {
                response = endUserController.handleAllTypes();
            }

            endUserController.sendResponse(exchange, 200, response);
        });

        server.setExecutor(null);
        server.start();

        EndUserController endUserController = new EndUserController();
        System.out.println(endUserController.handleDisplayReimbursements());

    }
}