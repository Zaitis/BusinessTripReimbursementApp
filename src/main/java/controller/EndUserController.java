package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Receipt;
import model.Reimbursement;
import service.ReceiptService;
import service.ReimbursementService;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Arrays;


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
//            } else if ("/enduser/editReimbursement".equals(path)) {
//                response = handleEditReimbursement(exchange);
//            } else if ("/enduser/addReceipts".equals(path)) {
//              //  response = handleAddReceipts(exchange);
            }
        }

        sendResponse(exchange, 200, response);
    }

    private String handleCreateReimbursement(HttpExchange exchange) {
        try {
            InputStream requestBody = exchange.getRequestBody();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            Reimbursement reimbursement = objectMapper.readValue(requestBody, Reimbursement.class);
            reimbursementService.addReimbursement(reimbursement);
            handleAddReceipts(exchange, reimbursement.getId());
            return "Reimbursement was been created with ID: " + reimbursement.getId();
        } catch (IOException e) {
            return "It is something wrong. "+ e;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private String handleAddReceipts(HttpExchange exchange, int id) {

        try {
            int reimbursementId = Integer.parseInt(exchange.getRequestHeaders().getFirst("ReimbursementId"));
            InputStream requestBody = exchange.getRequestBody();
            ObjectMapper objectMapper = new ObjectMapper();
            Receipt[] receipts = objectMapper.readValue(requestBody, Receipt[].class);
            for (Receipt receipt:receipts
                 ) {
                receipt.setReimbursementId(id);
                receiptService.addReceipt(receipt);

            }

            return "Receipts has been add to Reimbursement ID: " + reimbursementId;
        } catch (NumberFormatException | IOException e) {
            return "Something is wrong.";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String responseText) throws IOException {
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
