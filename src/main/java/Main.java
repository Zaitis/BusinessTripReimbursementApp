import com.sun.net.httpserver.HttpServer;
import controller.AdminController;
import controller.EndUserController;
import database.H2config;
import model.Receipt;
import model.Reimbursement;
import org.h2.tools.Server;
import service.ReceiptService;
import service.ReimbursementService;

import java.math.BigDecimal;
import java.net.InetSocketAddress;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        new H2config();
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/admin", new AdminController());
        server.createContext("/enduser", new EndUserController());

        server.setExecutor(null);
        server.start();

        Reimbursement reimbursement = new Reimbursement("Cyprian", "Norwid", LocalDateTime.now(), LocalDateTime.now(), BigDecimal.valueOf(200));
        ReimbursementService reimbursementService = new ReimbursementService();
        reimbursementService.addReimbursement(reimbursement);
        Receipt receipt = new Receipt(1,"Ticket", BigDecimal.valueOf(12.57));
        ReceiptService receiptService = new ReceiptService();
        receiptService.addReceipt(receipt);
        System.out.println(reimbursementService.getReimbursement(1));
    }
}