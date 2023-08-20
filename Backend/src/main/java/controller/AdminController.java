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
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;


public class AdminController {
    private final AdminService adminService = new AdminService();
    private final RateConfig rateConfig = RateConfig.getInstance();

    public AdminController() {}

    public String handleLogin(HttpExchange exchange) throws IOException {

        String responseMessage="";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode requestBody = objectMapper.readTree(exchange.getRequestBody());
            String username = requestBody.get("username").asText();
            String password = requestBody.get("password").asText();

            if (adminService.checkAuthenticate(username, password)) {
                System.out.println("1");
                responseMessage = "You are logged in as administrator.";
                sendResponse(exchange, 200, responseMessage);
            } else {
                responseMessage = "Authentication failed.";
                System.out.println("2");
                sendResponse(exchange, 401, responseMessage);
            }

        } catch (IOException e) {
            String errorMessage = "An error occurred while processing the request: " + e.getMessage();
            sendResponse(exchange, 500, errorMessage);
        }
        return responseMessage;
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
            EnumMap<Type, BigDecimal> receiptLimits = rateUpdateData.getReceiptLimits();

            settingRates(rateUpdateData, receiptLimits);

            return "Daily Allowance: " + rateConfig.getDailyAllowanceAmount() +
                    " Car Mileage: " + rateConfig.getCarMileageAmount() +
                    " Car Mileage Limit: " + rateConfig.getCarMileageLimit() +
                    " Receipt Limits: " + rateConfig.getReceiptTypeLimits();
        } catch (IOException e) {
            return "An error occurred while processing the request.";
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
