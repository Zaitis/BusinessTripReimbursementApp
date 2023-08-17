package service;

import model.Receipt;
import model.Reimbursement;
import repository.ReceiptRepository;

import java.sql.SQLException;
import java.util.List;

public class ReceiptService {

    private ReceiptRepository receiptRepository = new ReceiptRepository();

    public void addReceipt(Receipt receipt) throws SQLException {
        receiptRepository.addReceipt(receipt);
    }

    public List<Receipt> getReceiptsByReimbursementId(int id) {
        return null;
    }
}
