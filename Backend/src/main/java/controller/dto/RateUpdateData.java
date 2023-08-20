package controller.dto;

import model.Type;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.EnumMap;

public class RateUpdateData {

    private BigDecimal newDailyAllowanceAmount;
    private BigDecimal newCarMileageAmount;
    private BigDecimal newCarMileageLimit;

    private EnumMap<Type, BigDecimal> receiptLimits = new EnumMap<>(Type.class);

    public BigDecimal getNewDailyAllowanceAmount() {
        return newDailyAllowanceAmount;
    }

    public void setNewDailyAllowanceAmount(BigDecimal newDailyAllowanceAmount) {
        this.newDailyAllowanceAmount = newDailyAllowanceAmount;
    }

    public BigDecimal getNewCarMileageAmount() {
        return newCarMileageAmount;
    }

    public void setNewCarMileageAmount(BigDecimal newCarMileageAmount) {
        this.newCarMileageAmount = newCarMileageAmount;
    }

    public BigDecimal getNewCarMileageLimit() {
        return newCarMileageLimit;
    }

    public void setNewCarMileageLimit(BigDecimal newCarMileageLimit) {
        this.newCarMileageLimit = newCarMileageLimit;
    }

    public EnumMap<Type, BigDecimal> getReceiptLimits() {
        return new EnumMap<>(receiptLimits);
    }

    public void setReceiptLimits(EnumMap<Type, BigDecimal> receiptLimits) {
        if (receiptLimits != null) {
            this.receiptLimits.clear();
            this.receiptLimits.putAll(receiptLimits);
        }
    }

    public void setReceiptLimitForType(Type type, BigDecimal limit) {
        if (type != null && limit != null) {
            receiptLimits.put(type, limit);
        }
    }

    public BigDecimal getReceiptLimitForType(Type type) {
        return receiptLimits.get(type);
    }
}