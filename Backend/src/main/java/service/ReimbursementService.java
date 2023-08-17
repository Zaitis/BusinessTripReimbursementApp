package service;

import model.Reimbursement;
import repository.ReimbursementRepository;
import util.ReimbursementCalculator;

import java.sql.SQLException;
import java.util.List;

public class ReimbursementService {

    private ReimbursementRepository reimbursementRepository = new ReimbursementRepository();
    private ReimbursementCalculator reimbursementCalculator;

    public Reimbursement addReimbursement(Reimbursement reimbursement) throws SQLException {
       return reimbursementRepository.addReimbursement(reimbursement);
    }

    public Reimbursement getReimbursement(int id) throws Exception {
       return reimbursementRepository.getReimbursement(id).orElseThrow();
    }


    public List<Reimbursement> getAllReimbursements() throws SQLException {
        System.out.println(reimbursementRepository.getReimbursements());
        return reimbursementRepository.getReimbursements();
    }
}
