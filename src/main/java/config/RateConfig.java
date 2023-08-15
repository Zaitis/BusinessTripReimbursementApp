package config;

import java.math.BigDecimal;

public class RateConfig {
    private BigDecimal dailyAllowanceAmount = new BigDecimal(15);
    private BigDecimal carMileageAmount= new BigDecimal(0.3);

    public BigDecimal getDailyAllowanceAmount() { return dailyAllowanceAmount; }

    public BigDecimal getCarMileageAmount() { return carMileageAmount;}

    public RateConfig(){}
}
