package repository;

import database.H2config;
import model.Receipt;
import model.Reimbursement;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.DriverManager.getConnection;

public class ReimbursementRepository {

//        public void addReimbursement(Reimbursement reimbursement) throws SQLException {
//            String query = "INSERT INTO reimbursement VALUES (DEFAULT, ?, ?, ?, ?, ?)";
//
//            try (Connection connection = getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "sa");
//                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//
//                preparedStatement.setString(1, reimbursement.getFirstName());
//                preparedStatement.setString(2, reimbursement.getLastName());
//                preparedStatement.setTimestamp(3, Timestamp.valueOf(reimbursement.getStartDate()));
//                preparedStatement.setTimestamp(4, Timestamp.valueOf(reimbursement.getEndDate()));
//                preparedStatement.setBigDecimal(5, reimbursement.getDistanceDriven());
//
//                preparedStatement.executeUpdate(); // Wykonaj zapytanie
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }

    public Reimbursement addReimbursement(Reimbursement reimbursement) throws SQLException {
        String query = "INSERT INTO reimbursement VALUES (DEFAULT, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "sa");
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, reimbursement.getFirstName());
            preparedStatement.setString(2, reimbursement.getLastName());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(reimbursement.getStartDate()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(reimbursement.getEndDate()));
            preparedStatement.setBigDecimal(5, reimbursement.getDistanceDriven());

            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    reimbursement.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("An error occurred while created reimbursement.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reimbursement;
    }

    public Optional<Reimbursement> getReimbursement(int id) throws Exception {
        ResultSet resultSet = null;
        try (Connection connection = getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "sa");
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM reimbursement WHERE ID = ?")) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createReimbursement(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new RuntimeException("An error occurred while fetching reimbursement.", ex);
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }

    public List<Receipt> getReceiptsForReimbursement(int reimbursementId) throws Exception {
        List<Receipt> receipts = new ArrayList<>();
        ResultSet resultSet = null;

        try (Connection connection = getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "sa");
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM receipt WHERE REIMBURSEMENT_ID = ?")) {
            statement.setInt(1, reimbursementId);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Receipt receipt = createReceipt(resultSet);
                receipts.add(receipt);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("An error occurred while fetching receipts.", ex);
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
        return receipts;
    }

    private Receipt createReceipt(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("ID");
        int reimbursementId = resultSet.getInt("REIMBURSEMENT_ID");
        String type = resultSet.getString("TYPE");
        BigDecimal price = resultSet.getBigDecimal("PRICE");

        Receipt receipt = new Receipt();
        receipt.setId(id);
        receipt.setReimbursementId(reimbursementId);
        receipt.setType(type);
        receipt.setPrice(price);

        return receipt;
    }

    private Reimbursement createReimbursement(ResultSet resultSet) throws Exception {
        int id = resultSet.getInt("ID");
        String firstName = resultSet.getString("FIRST_NAME");
        String lastName = resultSet.getString("LAST_NAME");
        LocalDateTime startDate = resultSet.getTimestamp("START_DATE").toLocalDateTime();
        LocalDateTime endDate = resultSet.getTimestamp("END_DATE").toLocalDateTime();
        BigDecimal distanceDriven = resultSet.getBigDecimal("DISTANCE_DRIVEN");

        return new Reimbursement.Builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .startDate(startDate)
                .endDate(endDate)
                .receipts(getReceiptsForReimbursement(id))
                .distanceDriven(distanceDriven).build();
    }

}
