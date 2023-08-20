package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpExchange;
import config.RateConfig;
import controller.dto.RateUpdateData;
import model.Type;
import service.AdminService;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;

public class AdminController {
    private final AdminService adminService = new AdminService();
    private final RateConfig rateConfig = RateConfig.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AdminController() {}

    public void handleLogin(HttpExchange exchange) throws IOException {
        String responseMessage;
        try {
            JsonNode requestBody = objectMapper.readTree(exchange.getRequestBody());
            String username = requestBody.get("username").asText();
            String password = requestBody.get("password").asText();

            if (adminService.checkAuthenticate(username, password)) {
                responseMessage = "You are logged in as administrator.";
                sendResponse(exchange, 200, responseMessage);
            } else {
                responseMessage = "Authentication failed.";
                sendResponse(exchange, 401, responseMessage);
            }
        } catch (IOException e) {
            sendResponse(exchange, 500, "An error occurred while processing the request: " + e.getMessage());
        }
    }

    public void handleGetRates(HttpExchange exchange) throws IOException {
        try {
            ObjectNode responseNode = objectMapper.createObjectNode();
            responseNode.put("dailyAllowanceAmount", rateConfig.getDailyAllowanceAmount());
            responseNode.put("carMileageAmount", rateConfig.getCarMileageAmount());
            responseNode.put("carMileageLimit", rateConfig.getCarMileageLimit());

            ObjectNode receiptLimitsNode = objectMapper.createObjectNode();
            Map<Type, BigDecimal> receiptLimits = rateConfig.getReceiptTypeLimits();
            for (Map.Entry<Type, BigDecimal> entry : receiptLimits.entrySet()) {
                receiptLimitsNode.put(entry.getKey().toString(), entry.getValue());
            }
            responseNode.set("receiptLimits", receiptLimitsNode);

            sendResponse(exchange, 200, responseNode.toString());
        } catch (Exception e) {
            sendResponse(exchange, 500, "An error occurred while processing the request.");
        }
    }

    public void handleUpdateRates(HttpExchange exchange) throws IOException {
        try {
            RateUpdateData rateUpdateData = objectMapper.readValue(exchange.getRequestBody(), RateUpdateData.class);
            EnumMap<Type, BigDecimal> receiptLimits = rateUpdateData.getReceiptLimits();

            settingRates(rateUpdateData, receiptLimits);

            sendResponse(exchange, 200, "Daily Allowance: " + rateConfig.getDailyAllowanceAmount() +
                    " Car Mileage: " + rateConfig.getCarMileageAmount() +
                    " Car Mileage Limit: " + rateConfig.getCarMileageLimit() +
                    " Receipt Limits: " + rateConfig.getReceiptTypeLimits());
        } catch (IOException e) {
            sendResponse(exchange, 500, "An error occurred while processing the request.");
        }
    }

    private void settingRates(RateUpdateData rateUpdateData, EnumMap<Type, BigDecimal> receiptLimits) {
        rateConfig.setDailyAllowanceAmount(rateUpdateData.getNewDailyAllowanceAmount());
        rateConfig.setCarMileageAmount(rateUpdateData.getNewCarMileageAmount());
        rateConfig.setCarMileageLimit(rateUpdateData.getNewCarMileageLimit());
        for (Map.Entry<Type, BigDecimal> entry : receiptLimits.entrySet()) {
            rateConfig.setLimitForType(entry.getKey(), entry.getValue());
        }
    }

    public void sendResponse(HttpExchange exchange, int statusCode, String responseText) throws IOException {
        byte[] responseBytes = responseText.getBytes("UTF-8");
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        try (OutputStream outputStream = exchange.getResponseBody()) {
            outputStream.write(responseBytes);
        }
    }
}