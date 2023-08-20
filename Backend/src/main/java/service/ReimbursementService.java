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

    private final ReimbursementRepository reimbursementRepository;
    private final ReimbursementCalculator reimbursementCalculator;
    private final RateConfig rateConfig;

    public ReimbursementService(
            ReimbursementRepository reimbursementRepository,
            ReimbursementCalculator reimbursementCalculator,
            RateConfig rateConfig) {
        this.reimbursementRepository = reimbursementRepository;
        this.reimbursementCalculator = reimbursementCalculator;
        this.rateConfig = rateConfig;
    }

    public Reimbursement addReimbursement(Reimbursement reimbursement) {
        return reimbursementRepository.addReimbursement(reimbursement);
    }

    public List<ReimbursementDto> getAllReimbursementsWithTotal() {
        List<ReimbursementDto> lists = new ArrayList<>();
        try {
            List<Reimbursement> reimbursements= reimbursementRepository.getReimbursements();
            for (Reimbursement reimbursement: reimbursements) {
                int days = reimbursementCalculator.calculateDaysDifference(
                        reimbursement.getStartDate(),
                        reimbursement.getEndDate());

                BigDecimal total = reimbursementCalculator.calculateTotalReimbursement(
                        days,
                        reimbursement.getDistanceDriven(),
                        reimbursement.getReceipts(),
                        rateConfig
                );

                lists.add(getReimbursementDto(reimbursement, total));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching reimbursements with total.", e);
        }
        return lists;
    }

    private static ReimbursementDto getReimbursementDto(Reimbursement reimbursement, BigDecimal total) {
        return new ReimbursementDto.Builder()
                .id(reimbursement.getId())
                .firstName(reimbursement.getFirstName())
                .lastName(reimbursement.getLastName())
                .startDate(reimbursement.getStartDate())
                .endDate(reimbursement.getEndDate())
                .distanceDriven(reimbursement.getDistanceDriven())
                .receipts(reimbursement.getReceipts())
                .total(total)
                .build();
    }

    public Reimbursement getReimbursement(int id) {
        try {
            return reimbursementRepository.getReimbursement(id).orElseThrow(
                    () -> new RuntimeException("Reimbursement not found with ID: " + id)
            );
        } catch (Exception e) {
            throw new RuntimeException("Error fetching reimbursement.", e);
        }
    }
}