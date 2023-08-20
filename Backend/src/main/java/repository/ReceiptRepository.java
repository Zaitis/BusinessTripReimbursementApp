package repository;

import model.Receipt;
import model.Type;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ReceiptRepository {

    public void addReceipt(Receipt receipt) throws SQLException {
            String query = "INSERT INTO receipt VALUES (DEFAULT, ?, ?, ?)";

            try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "sa");
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, receipt.getReimbursementId());
                preparedStatement.setString(2, String.valueOf(receipt.getType()));
                preparedStatement.setBigDecimal(3, receipt.getPrice());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    public Receipt createReceipt(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("ID");
        int reimbursementId = resultSet.getInt("REIMBURSEMENT_ID");
        Type type = Type.valueOf(resultSet.getString("TYPE"));
        BigDecimal price = resultSet.getBigDecimal("PRICE");

        Receipt receipt = new Receipt();
        receipt.setId(id);
        receipt.setReimbursementId(reimbursementId);
        receipt.setType(type);
        receipt.setPrice(price);

        return receipt;
    }

    public List<Receipt> getReceiptByReimbursementId(int id){
        return null;
    }
}


