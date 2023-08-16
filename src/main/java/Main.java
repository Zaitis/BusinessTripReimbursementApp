import com.sun.net.httpserver.HttpServer;
import config.AdminConfig;
import config.RateConfig;
import controller.AdminController;
import controller.EndUserController;
import database.H2config;
import model.Receipt;
import model.Reimbursement;
import service.ReceiptService;
import service.ReimbursementService;
import util.AdminAuthenticator;

import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws Exception {
        new H2config();
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/admin", new AdminController(new AdminAuthenticator(new AdminConfig()), new RateConfig()));
        server.createContext("/enduser", new EndUserController());

        server.setExecutor(null);
        server.start();

        Reimbursement reimbursement = new Reimbursement.Builder()
                .firstName("Cyprian")
                .lastName("Norwid")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now())
                .distanceDriven(BigDecimal.valueOf(200))
                .build();
        ReimbursementService reimbursementService = new ReimbursementService();
        reimbursementService.addReimbursement(reimbursement);
        Receipt receipt = new Receipt(1, "Ticket", BigDecimal.valueOf(12.57));
        ReceiptService receiptService = new ReceiptService();
        receiptService.addReceipt(receipt);
    }
}