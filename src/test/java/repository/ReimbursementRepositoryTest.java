package repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import model.Reimbursement;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;

class ReimbursementRepositoryTest {



    @Test
    void shouldAddReimbursementToDB() throws SQLException {
        ReimbursementRepository reimbursementRepository = new ReimbursementRepository();
        Reimbursement reimbursement = new Reimbursement("Cyprian", "Norwid", LocalDateTime.now(), LocalDateTime.now(), BigDecimal.valueOf(200));
        reimbursementRepository.addReimbursement(reimbursement);
        assertEquals(0, reimbursement.getId());
    }


}

