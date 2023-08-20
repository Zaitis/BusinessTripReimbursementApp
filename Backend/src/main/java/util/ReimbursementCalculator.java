package util;

import config.RateConfig;
import model.Receipt;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Utility class for calculating reimbursements.
 */
public class ReimbursementCalculator {

    private static final int SCALE = 2;

    /**
     * Calculates the total reimbursement based on the number of days, total distance driven, and receipts provided.
     *
     * @param numberOfDays       the number of days.
     * @param totalDistanceDriven the total distance driven.
     * @param receipts           the list of receipts.
     * @param rateConfig         the rate configuration.
     * @return the total reimbursement.
     */
    public BigDecimal calculateTotalReimbursement(final int numberOfDays, final BigDecimal totalDistanceDriven, final List<Receipt> receipts, final RateConfig rateConfig) {
        BigDecimal totalReimbursement = receipts.stream()
                .map(Receipt::getPrice)
                .filter(price -> price != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal dailyReimbursement = BigDecimal.valueOf(numberOfDays).multiply(rateConfig.getDailyAllowanceAmount());
        BigDecimal distanceReimbursement = totalDistanceDriven.multiply(rateConfig.getCarMileageAmount());

        totalReimbursement = totalReimbursement.add(dailyReimbursement).add(distanceReimbursement);

        return totalReimbursement.setScale(SCALE, RoundingMode.CEILING);
    }

    /**
     * Calculates the difference in days between the start and end date.
     *
     * @param startDate the start date.
     * @param endDate   the end date.
     * @return the number of days difference.
     */
    public int calculateDaysDifference(final LocalDateTime startDate, final LocalDateTime endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        return Math.toIntExact(days);
    }
}