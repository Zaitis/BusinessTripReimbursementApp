import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import controller.AdminController;
import controller.EndUserController;
import database.H2config;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {

    private static final int SERVER_PORT = 8000;
    private static final String LOCAL_ORIGIN = "http://localhost:3000";

    public static void main(String[] args) throws Exception {
        new H2config();
        HttpServer server = HttpServer.create(new InetSocketAddress(SERVER_PORT), 0);

        AdminController adminController = new AdminController();
        EndUserController endUserController = new EndUserController();

        server.createContext("/admin", exchange -> handleAdminContext(exchange, adminController));
        server.createContext("/enduser", exchange -> handleEndUserContext(exchange, endUserController));

        server.setExecutor(null);
        server.start();
    }

    private static void handleAdminContext(HttpExchange exchange, AdminController adminController) throws IOException {
        setCORSHeaders(exchange);
        if (isOptionsRequest(exchange)) {
            sendOptionsResponse(exchange);
            return;
        }

        String path = exchange.getRequestURI().getPath();
        String response = "";

        switch (path) {
            case "/admin/login":
                adminController.handleLogin(exchange);
                break;
            case "/admin/updateRates":
                adminController.handleUpdateRates(exchange);
                break;
            case "/admin/getRates":
                adminController.handleGetRates(exchange);
                break;
            default:
                response = "Not Found";
                break;
        }

        adminController.sendResponse(exchange, 200, response);
    }

    private static void handleEndUserContext(HttpExchange exchange, EndUserController endUserController) throws IOException {
        setCORSHeaders(exchange);
        if (isOptionsRequest(exchange)) {
            sendOptionsResponse(exchange);
            return;
        }

        String path = exchange.getRequestURI().getPath();
        String response = "";

        switch (path) {
            case "/enduser/createReimbursement":
                response = endUserController.handleCreateReimbursement(exchange);
                break;
            case "/enduser/displayReimbursements":
                response = endUserController.handleDisplayReimbursements(exchange);
                break;
            case "/enduser/getTypes":
                response = endUserController.handleAllTypes();
                break;
            default:
                response = "Not Found";
                break;
        }

        endUserController.sendResponse(exchange, 200, response);
    }

    private static void setCORSHeaders(HttpExchange exchange) {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", LOCAL_ORIGIN);
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Credentials", "true");
    }

    private static boolean isOptionsRequest(HttpExchange exchange) {
        return exchange.getRequestMethod().equalsIgnoreCase("OPTIONS");
    }

    private static void sendOptionsResponse(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(204, -1);
    }
}