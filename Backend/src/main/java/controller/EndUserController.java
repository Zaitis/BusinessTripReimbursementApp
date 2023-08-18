package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Receipt;
import model.Reimbursement;
import service.ReceiptService;
import service.ReimbursementService;
import service.dto.ReimbursementDto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class EndUserController implements HttpHandler {

    private ReimbursementService reimbursementService = new ReimbursementService();
    private ReceiptService receiptService = new ReceiptService();


    public EndUserController() {
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        String response = "";

        if ("POST".equals(method)) {
            if ("/enduser/createReimbursement".equals(path)) {
                response = handleCreateReimbursement(exchange);
            }
        } else if ("GET".equals(method)) {
            if ("/enduser/displayReimbursements".equals(path)) {
                response = handleDisplayReimbursements();
            }
        }
        sendResponse(exchange, 200, response);
    }


    public String handleCreateReimbursement(HttpExchange exchange) {

        try {
            InputStream requestBody = exchange.getRequestBody();
            byte[] requestBodyBytes = requestBody.readAllBytes();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            Reimbursement reimbursement = objectMapper.readValue(requestBodyBytes, Reimbursement.class);
            reimbursementService.addReimbursement(reimbursement);
            JsonNode rootNode = objectMapper.readTree(requestBodyBytes);
            JsonNode receiptsNode = rootNode.get("receipts");
            if (receiptsNode != null && receiptsNode.isArray()) {
                for (JsonNode receiptNode : receiptsNode) {
                    Receipt receipt = objectMapper.treeToValue(receiptNode, Receipt.class);
                    receipt.setReimbursementId(reimbursement.getId());
                    receiptService.addReceipt(receipt);
                }
            }
            return "Reimbursement was created with ID: " + reimbursement.getId();
        } catch (IOException e) {
            return "An error occurred while processing the request: " + e.getMessage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public String handleDisplayReimbursements() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            List<ReimbursementDto> reimbursementsDto = reimbursementService.getAllReimbursementsWithTotal();
            List<JsonNode> reimbursementNodes = new ArrayList<>();

            for (ReimbursementDto reimbursementDto : reimbursementsDto) {
                JsonNode receiptsNode = objectMapper.valueToTree(reimbursementDto.getReceipts());
                JsonNode reimbursementNode = objectMapper.valueToTree(reimbursementDto);
                ((ObjectNode) reimbursementNode).replace("receipts", receiptsNode);
                reimbursementNodes.add(reimbursementNode);
            }

            return objectMapper.writeValueAsString(reimbursementNodes);
        } catch (IOException e) {
            return "An error occurred while processing the request: " + e.getMessage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendResponse(HttpExchange exchange, int statusCode, String responseText) throws IOException {
        byte[] responseBytes = responseText.getBytes("UTF-8");
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        OutputStream outputStream = exchange.getResponseBody();
        try {
            outputStream.write(responseBytes);
        } finally {
            outputStream.close();
        }
    }
}
