package repository;

import model.Receipt;
import model.Reimbursement;

import java.math.BigDecimal;
import java.sql.Connection;
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
    ReceiptRepository receiptRepository = new ReceiptRepository();

    public Reimbursement addReimbursement(Reimbursement reimbursement) {
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
                return Optional.of(buildReimbursement(
                        resultSet.getInt("ID"),
                        resultSet.getString("FIRST_NAME"),
                        resultSet.getString("LAST_NAME"),
                        resultSet.getTimestamp("START_DATE").toLocalDateTime(),
                        resultSet.getTimestamp("END_DATE").toLocalDateTime(),
                        resultSet.getBigDecimal("DISTANCE_DRIVEN")));
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

    public List<Reimbursement> getReimbursements() {
        List<Reimbursement> reimbursements = new ArrayList<>();
        String query = "SELECT * FROM reimbursement";

        try (Connection connection = getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "sa");
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String firstName = resultSet.getString("FIRST_NAME");
                String lastName = resultSet.getString("LAST_NAME");
                LocalDateTime startDate = resultSet.getTimestamp("START_DATE").toLocalDateTime();
                LocalDateTime endDate = resultSet.getTimestamp("END_DATE").toLocalDateTime();
                BigDecimal distanceDriven = resultSet.getBigDecimal("DISTANCE_DRIVEN");
                Reimbursement reimbursement = buildReimbursement(id, firstName, lastName, startDate, endDate, distanceDriven);
                reimbursements.add(reimbursement);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return reimbursements;
    }


    public List<Receipt> getReceiptsForReimbursement(int reimbursementId) throws Exception {
        List<Receipt> receipts = new ArrayList<>();
        ResultSet resultSet = null;

        try (Connection connection = getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "sa");
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM receipt WHERE REIMBURSEMENT_ID = ?")) {
            statement.setInt(1, reimbursementId);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Receipt receipt = receiptRepository.createReceipt(resultSet);
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

    private Reimbursement buildReimbursement(int id, String firstName, String lastName, LocalDateTime startDate, LocalDateTime endDate, BigDecimal distanceDriven) throws Exception {
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
