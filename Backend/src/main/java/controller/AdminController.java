package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpExchange;
import config.RateConfig;
import controller.dto.RateUpdateData;
import model.Type;
import util.AdminAuthenticator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;


public class AdminController {
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

    public String handleGetRates() {
        try {
            RateConfig rateConfig = RateConfig.getInstance();

            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode responseNode = objectMapper.createObjectNode();

            responseNode.put("dailyAllowanceAmount", rateConfig.getDailyAllowanceAmount());
            responseNode.put("carMileageAmount", rateConfig.getCarMileageAmount());
            responseNode.put("carMileageLimit", rateConfig.getCarMileageLimit());

            ObjectNode receiptLimitsNode = objectMapper.createObjectNode();
            EnumMap<Type, BigDecimal> receiptLimits = rateConfig.getReceiptTypeLimits();
            for (Map.Entry<Type, BigDecimal> entry : receiptLimits.entrySet()) {
                receiptLimitsNode.put(entry.getKey().toString(), entry.getValue());
            }
            responseNode.set("receiptLimits", receiptLimitsNode);
            return responseNode.toString();
        } catch (Exception e) {
            return "An error occurred while processing the request.";
        }
    }

    public String handleUpdateRates(HttpExchange exchange) {
        try {
            InputStream requestBody = exchange.getRequestBody();
            ObjectMapper objectMapper = new ObjectMapper();
            RateUpdateData rateUpdateData = objectMapper.readValue(requestBody, RateUpdateData.class);

            RateConfig rateConfig = RateConfig.getInstance();

            rateConfig.setDailyAllowanceAmount(rateUpdateData.getNewDailyAllowanceAmount());
            rateConfig.setCarMileageAmount(rateUpdateData.getNewCarMileageAmount());
            rateConfig.setCarMileageLimit(rateUpdateData.getNewCarMileageLimit());

            EnumMap<Type, BigDecimal> receiptLimits = rateUpdateData.getReceiptLimits();
            for (Map.Entry<Type, BigDecimal> entry : receiptLimits.entrySet()) {
                rateConfig.setLimitForType(entry.getKey(), entry.getValue());
            }

            return "Daily Allowance: " + rateConfig.getDailyAllowanceAmount() +
                    " Car Mileage: " + rateConfig.getCarMileageAmount() +
                    " Car Mileage Limit: " + rateConfig.getCarMileageLimit() +
                    " Receipt Limits: " + rateConfig.getReceiptTypeLimits();
        } catch (IOException e) {
            return "An error occurred while processing the request.";
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
}
