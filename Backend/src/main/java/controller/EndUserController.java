package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.net.httpserver.HttpExchange;
import config.RateConfig;
import model.Receipt;
import model.Reimbursement;
import model.Type;
import repository.ReimbursementRepository;
import service.ReceiptService;
import service.ReimbursementService;
import service.dto.ReimbursementDto;
import util.ReceiptValidator;
import util.ReimbursementCalculator;
import util.ReimbursementValidator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class EndUserController {
    private final ReimbursementService reimbursementService = new ReimbursementService(new ReimbursementRepository(), new ReimbursementCalculator(), RateConfig.getInstance());
    private final ReceiptService receiptService = new ReceiptService();
    private final ReceiptValidator receiptValidator = new ReceiptValidator();
    private final ReimbursementCalculator reimbursementCalculator = new ReimbursementCalculator();
    private final ReimbursementValidator reimbursementValidator = new ReimbursementValidator();
    private final ObjectMapper objectMapper;

    public EndUserController() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
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
                    if (!reimbursementValidator.isValidDate(reimbursement)) {
                        String responseMessage = "Start day is greater than end day.";
                        sendResponse(exchange, 400, responseMessage);
                    }
                    if (!receiptValidator.isValidAmount(receipt)) {
                        String responseMessage = "Invalid Amount.";
                        sendResponse(exchange, 400, responseMessage);
                    }
                    if (!receiptValidator.isWithinLimits(RateConfig.getInstance(), receipt)) {
                        String responseMessage = "Price for receipt is too much.";
                        sendResponse(exchange, 400, responseMessage);
                    }
                    receipt.setReimbursementId(reimbursement.getId());
                    receiptService.addReceipt(receipt);
                }
            }
            return String.valueOf(reimbursementCalculator.calculateTotalReimbursement(reimbursementCalculator.calculateDaysDifference(
                            reimbursement.getStartDate(), reimbursement.getEndDate()),
                    reimbursement.getDistanceDriven(),
                    reimbursement.getReceipts(),
                    RateConfig.getInstance()));
        } catch (IOException e) {
            return "An error occurred while processing the request: " + e.getMessage();
        }
    }


    public String handleDisplayReimbursements(HttpExchange exchange) throws IOException {
        try {
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
            sendResponse(exchange, 400, "Invalid request data: " + e.getMessage());
        }
        return null;
    }

    public String handleAllTypes() {
        try {
            return objectMapper.writeValueAsString(Type.values());
        } catch (Exception e) {
            return "An error occurred while processing the request.";
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