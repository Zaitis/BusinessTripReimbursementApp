package util;

import model.Reimbursement;

/**
 * Utility class for validating Reimbursement properties.
 */
public class ReimbursementValidator {

    /**
     * Validates if the end date of the reimbursement is on or after the start date.
     *
     * @param reimbursement the reimbursement instance to validate.
     * @return true if valid, false otherwise.
     */
    public boolean isValidDate(final Reimbursement reimbursement) {
        if (reimbursement == null || reimbursement.getEndDate() == null || reimbursement.getStartDate() == null) {
            return false;
        }
        return reimbursement.getEndDate().compareTo(reimbursement.getStartDate()) >= 0;
    }
}