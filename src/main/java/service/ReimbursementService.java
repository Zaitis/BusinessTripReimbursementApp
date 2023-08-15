package service;

import model.Reimbursement;
import repository.ReimbursementRepository;
import util.ReimbursementCalculator;

import java.sql.SQLException;

public class ReimbursementService {

    private ReimbursementRepository reimbursementRepository = new ReimbursementRepository();
    private ReimbursementCalculator reimbursementCalculator;

    public void addReimbursement(Reimbursement reimbursement) throws SQLException {
        reimbursementRepository.addReimbursement(reimbursement);
    }

    public Reimbursement getReimbursement(int id) throws Exception {
       return reimbursementRepository.getReimbursement(id).orElseThrow();
    }



}
