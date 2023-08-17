package util;

import config.RateConfig;
import model.Receipt;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class ReimbursementCalculator {

    public BigDecimal calculateTotalReimbursement(int numberOfDays, BigDecimal totalDistanceDriven, List<Receipt> receipts, RateConfig rateConfig) {
        BigDecimal totalReimbursement = BigDecimal.ZERO;

        for (Receipt receipt : receipts) {
            totalReimbursement = totalReimbursement.add(receipt.getPrice());
        }

        BigDecimal dailyReimbursement = BigDecimal.valueOf(numberOfDays).multiply(rateConfig.getDailyAllowanceAmount());
        BigDecimal distanceReimbursement = totalDistanceDriven.multiply(rateConfig.getCarMileageAmount());

        totalReimbursement = totalReimbursement.add(dailyReimbursement).add(distanceReimbursement);
        return totalReimbursement.setScale(2, RoundingMode.CEILING);
    }
}