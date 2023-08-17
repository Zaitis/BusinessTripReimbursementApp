package controller;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import com.fasterxml.jackson.databind.JsonNode;
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





    public String handleLogin(HttpExchange exchange) throws IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode requestBody = objectMapper.readTree(exchange.getRequestBody());

            String username = requestBody.get("username").asText();
            String password = requestBody.get("password").asText();
            String responseMessage = "";

            if (adminAuthenticator.authenticate(username, password)) {
                responseMessage = "You are logged in as administrator.";
                sendResponse(exchange, 200, responseMessage);
            } else {
                responseMessage = "Authentication failed.";
                sendResponse(exchange, 401, responseMessage);
            }
        } catch (IOException e) {
            String errorMessage = "An error occurred while processing the request: " + e.getMessage();
            sendResponse(exchange, 500, errorMessage);
        }
        return null;
    }


    public String handleUpdateRates(HttpExchange exchange) {
        System.out.println("tu jesteśmy");
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
    public void sendResponse(HttpExchange exchange, int statusCode, String responseText) throws IOException {
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
