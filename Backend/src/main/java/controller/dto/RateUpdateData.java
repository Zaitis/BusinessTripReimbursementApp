package controller.dto;

import java.math.BigDecimal;

public class RateUpdateData {
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

    private BigDecimal newDailyAllowanceAmount;
    private BigDecimal newCarMileageAmount;


}