package util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import model.Reimbursement;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class ReimbursementValidatorTest {


    @Test
    void shouldEndDateGreaterThanStartDate() {
        ReimbursementValidator reimbursementValidator = new ReimbursementValidator();

        Reimbursement reimbursement = new Reimbursement();
        reimbursement.setEndDate(LocalDateTime.of(2023, 12, 12, 0, 0));
        reimbursement.setStartDate(LocalDateTime.of(2023, 12, 1, 0, 0));
        assertTrue(reimbursementValidator.isValidDate(reimbursement));
    }

    @Test
    void shouldStartDateGreaterThanEndDate() {
        ReimbursementValidator reimbursementValidator = new ReimbursementValidator();

        Reimbursement reimbursement = new Reimbursement();
        reimbursement.setEndDate(LocalDateTime.of(2023, 1, 1, 0, 0));
        reimbursement.setStartDate(LocalDateTime.of(2023, 1, 10, 0, 0));
        assertFalse(reimbursementValidator.isValidDate(reimbursement));
    }

    @Test
    void shouldStartDateThisSameThanEndDate() {
        ReimbursementValidator reimbursementValidator = new ReimbursementValidator();

        Reimbursement reimbursement = new Reimbursement();
        reimbursement.setEndDate(LocalDateTime.of(2023, 1, 1, 0, 0));
        reimbursement.setStartDate(LocalDateTime.of(2023, 1, 1, 0, 0));
        assertTrue(reimbursementValidator.isValidDate(reimbursement));
    }
}

