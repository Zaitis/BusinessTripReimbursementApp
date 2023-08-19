package controller.dto;

import model.Type;

import java.math.BigDecimal;
import java.util.EnumMap;

public class RateUpdateData {

    private BigDecimal newDailyAllowanceAmount;
    private BigDecimal newCarMileageAmount;
    private BigDecimal newCarMileageLimit;
    private EnumMap<Type, BigDecimal> receiptLimits;

    public BigDecimal getNewDailyAllowanceAmount() {
        return newDailyAllowanceAmount;
    }

    public void setNewDailyAllowanceAmount(BigDecimal newDailyAllowanceAmount) {
        this.newDailyAllowanceAmount = newDailyAllowanceAmount;
    }

    public void setNewCarMileageAmount(BigDecimal newCarMileageAmount) {
        this.newCarMileageAmount = newCarMileageAmount;
    }

    public BigDecimal getNewCarMileageAmount() {
        return newCarMileageAmount;
    }

    public BigDecimal getNewCarMileageLimit() {
        return newCarMileageLimit;
    }

    public void setNewCarMileageLimit(BigDecimal newCarMileageLimit) {
        this.newCarMileageLimit = newCarMileageLimit;
    }

    public EnumMap<Type, BigDecimal> getReceiptLimits() {
        return receiptLimits;
    }

    public void setReceiptLimits(EnumMap<Type, BigDecimal> receiptLimits) {
        this.receiptLimits = receiptLimits;
    }
}