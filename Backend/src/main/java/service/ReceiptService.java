package service;

import model.Receipt;
import repository.ReceiptRepository;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class ReceiptService {

    private final ReceiptRepository receiptRepository;

    public ReceiptService(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    public void addReceipt(Receipt receipt) {
        try {
            receiptRepository.addReceipt(receipt);
        } catch (SQLException e) {
            throw new RuntimeException("Error adding receipt.", e);
        }
    }

    public List<Receipt> getReceiptsByReimbursementId(int id) {
        return Collections.emptyList();
    }

    public ReceiptService() {
        this.receiptRepository = new ReceiptRepository();
    }
}