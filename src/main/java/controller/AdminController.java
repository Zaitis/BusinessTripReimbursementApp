package controller;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import config.RateConfig;
import controller.dto.RateUpdateData;
import util.AdminAuthenticator;

public class AdminController implements HttpHandler {
    private AdminAuthenticator adminAuthenticator;
    private RateConfig rateConfig;

    public AdminController(AdminAuthenticator adminAuthenticator, RateConfig rateConfig) {
        this.adminAuthenticator = adminAuthenticator;
        this.rateConfig = rateConfig;
    }

    private String handleLogin(HttpExchange exchange) {
        String username = exchange.getRequestHeaders().getFirst("Username");
        String password = exchange.getRequestHeaders().getFirst("Password");

        if (adminAuthenticator.authenticate(username, password)) {
            return "You are log in as administrator.";
        } else {
            return "Authentication failed.";
        }
    }

    private String handleUpdateRates(HttpExchange exchange) {
        try {
            InputStream requestBody = exchange.getRequestBody();
            ObjectMapper objectMapper = new ObjectMapper();
            RateUpdateData rateUpdateData = objectMapper.readValue(requestBody, RateUpdateData.class);
            rateConfig.setDailyAllowanceAmount(rateUpdateData.getNewDailyAllowanceAmount());
            rateConfig.setCarMileageAmount(rateUpdateData.getNewCarMileageAmount());


            return"Daily Allowance: " + rateConfig.getDailyAllowanceAmount() + " Car Millage: " + rateConfig.getCarMileageAmount();
        } catch (IOException e) {
            return "It is something wrong.";
        }
    }
    private void sendResponse(HttpExchange exchange, int statusCode, String responseText) throws IOException {
        byte[] responseBytes = responseText.getBytes("UTF-8");
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        OutputStream outputStream = exchange.getResponseBody();
        try (PrintWriter writer = new PrintWriter(outputStream)) {
            writer.write(responseText);
            writer.flush();
        }
    }


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String response = "";

        if ("POST".equals(method)) {
            String path = exchange.getRequestURI().getPath();
            if ("/admin/login".equals(path)) {
                response = handleLogin(exchange);
            } else if ("/admin/updateRates".equals(path)) {
                response = handleUpdateRates(exchange);
            }
        }

        sendResponse(exchange, 200, response);
    }

}
