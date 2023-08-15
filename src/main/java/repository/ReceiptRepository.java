package repository;

import model.Receipt;
import model.Reimbursement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class ReceiptRepository {
    public void addReceipt(Receipt receipt) throws SQLException {
            String query = "INSERT INTO receipt VALUES (DEFAULT, ?, ?, ?)";

            try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "sa");
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, receipt.getReimbursementId());
                preparedStatement.setString(2, receipt.getType());
                preparedStatement.setBigDecimal(3, receipt.getPrice());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    public List<Receipt> getReceiptByReimbursementId(int id){
        return null;
    }
}


