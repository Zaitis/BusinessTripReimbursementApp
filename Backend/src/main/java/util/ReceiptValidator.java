package util;

import config.RateConfig;
import model.Receipt;

import java.math.BigDecimal;

public class ReceiptValidator {


    public boolean isValidAmount;
    public boolean isWithinLimits;


    public ReceiptValidator(){
    }

    public boolean isValidAmount(Receipt receipt) {
        return receipt.getPrice() != null && receipt.getPrice().compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isWithinLimits(RateConfig rateConfig, Receipt receipt) {
        return rateConfig.getReceiptTypeLimits().containsKey(receipt.getType()) &&
                receipt.getPrice() != null &&
                receipt.getPrice().compareTo(rateConfig.getReceiptTypeLimits().get(receipt.getType())) <= 0;
    }
}

