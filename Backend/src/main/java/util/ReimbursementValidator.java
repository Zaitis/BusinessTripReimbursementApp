package util;

import model.Reimbursement;

public class ReimbursementValidator {

    public boolean isValidDate(Reimbursement reimbursement){
        return (reimbursement.getEndDate().compareTo(reimbursement.getStartDate())>=0);
    }




}
