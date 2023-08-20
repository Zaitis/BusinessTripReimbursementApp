package util;

import config.RateConfig;
import model.Receipt;

import java.math.BigDecimal;

/**
 * Utility class responsible for validating receipt details.
 */
public class ReceiptValidator {

    /**
     * Constructs a ReceiptValidator.
     */
    public ReceiptValidator() {
    }

    /**
     * Validates if the amount in the receipt is valid.
     *
     * @param receipt the receipt to be validated.
     * @return true if the amount is non-null and non-negative, false otherwise.
     */
    public boolean isValidAmount(final Receipt receipt) {
        if (receipt == null || receipt.getPrice() == null) {
            return false;
        }
        return receipt.getPrice().compareTo(BigDecimal.ZERO) >= 0;
    }

    /**
     * Validates if the receipt is within the predefined limits of the rate configuration.
     *
     * @param rateConfig the configuration containing receipt type limits.
     * @param receipt the receipt to be validated.
     * @return true if the receipt is within limits, false otherwise.
     */
    public boolean isWithinLimits(final RateConfig rateConfig, final Receipt receipt) {
        if (rateConfig == null || receipt == null) {
            return false;
        }

        BigDecimal receiptPrice = receipt.getPrice();
        BigDecimal receiptTypeLimit = rateConfig.getReceiptTypeLimits().get(receipt.getType());

        return rateConfig.getReceiptTypeLimits().containsKey(receipt.getType()) &&
                isValidAmount(receipt) &&
                receiptPrice != null &&
                (receiptTypeLimit != null && receiptPrice.compareTo(receiptTypeLimit) <= 0);
    }
}