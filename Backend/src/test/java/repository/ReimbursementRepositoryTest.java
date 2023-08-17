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
        Reimbursement reimbursement = new Reimbursement.Builder()
                .firstName("Cyprian")
                .lastName("Norwid")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now())
                .distanceDriven(BigDecimal.valueOf(200))
                .build();
        reimbursementRepository.addReimbursement(reimbursement);
        assertEquals(0, reimbursement.getId());
    }


}

