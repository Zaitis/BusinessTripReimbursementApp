package service;

import config.RateConfig;
import model.Reimbursement;
import repository.ReimbursementRepository;
import service.dto.ReimbursementDto;
import util.ReimbursementCalculator;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementService {

    private ReimbursementRepository reimbursementRepository = new ReimbursementRepository();
    private ReimbursementCalculator reimbursementCalculator = new ReimbursementCalculator();

    public Reimbursement addReimbursement(Reimbursement reimbursement) throws SQLException {
       return reimbursementRepository.addReimbursement(reimbursement);
    }

    public List<ReimbursementDto> getAllReimbursementsWithTotal() throws SQLException {
        int days=0;
        List<ReimbursementDto> lists = new ArrayList<>();
        List<Reimbursement> reimbursements= reimbursementRepository.getReimbursements();

        for (Reimbursement reimbursement: reimbursements) {

            days = reimbursementCalculator.calculateDaysDifference(reimbursement.getStartDate(), reimbursement.getEndDate());

            BigDecimal total = reimbursementCalculator.calculateTotalReimbursement(
                    days,
                    reimbursement.getDistanceDriven(),
                    reimbursement.getReceipts(),
                    RateConfig.getInstance()
            );
            ReimbursementDto reimbursementDto = getReimbursementDto(reimbursement, total);
            lists.add(reimbursementDto);
        }
        return lists;
    }

    private static ReimbursementDto getReimbursementDto(Reimbursement reimbursement, BigDecimal total) {
        ReimbursementDto reimbursementDto = new ReimbursementDto.Builder()
                .id(reimbursement.getId())
                .firstName(reimbursement.getFirstName())
                .lastName(reimbursement.getLastName())
                .startDate(reimbursement.getStartDate())
                .endDate(reimbursement.getEndDate())
                .distanceDriven(reimbursement.getDistanceDriven())
                .receipts(reimbursement.getReceipts())
                .total(total)
                .build();
        return reimbursementDto;
    }

    public Reimbursement getReimbursement(int id) throws Exception {
        return reimbursementRepository.getReimbursement(id).orElseThrow();
    }
}
