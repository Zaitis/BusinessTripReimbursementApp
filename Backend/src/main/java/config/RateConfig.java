//package config;
//
//import java.math.BigDecimal;
//
//public class RateConfig {
//    private BigDecimal dailyAllowanceAmount = new BigDecimal(15);
//    private BigDecimal carMileageAmount= new BigDecimal(0.3);
//
//    public BigDecimal getDailyAllowanceAmount() { return dailyAllowanceAmount; }
//
//    public BigDecimal getCarMileageAmount() { return carMileageAmount;}
//
//    public RateConfig(){}
//
//    public void setDailyAllowanceAmount(BigDecimal dailyAllowanceAmount) {
//        this.dailyAllowanceAmount = dailyAllowanceAmount;
//    }
//
//    public void setCarMileageAmount(BigDecimal carMileageAmount) {
//        this.carMileageAmount = carMileageAmount;
//    }
//}


package config;

import java.math.BigDecimal;

public class RateConfig {
    private BigDecimal dailyAllowanceAmount = new BigDecimal(15);
    private BigDecimal carMileageAmount = new BigDecimal(0.3);


    private static RateConfig instance;


    private RateConfig() {}

    public static RateConfig getInstance() {
        if (instance == null) {
            instance = new RateConfig();
        }
        return instance;
    }

    public BigDecimal getDailyAllowanceAmount() {
        return dailyAllowanceAmount;
    }

    public BigDecimal getCarMileageAmount() {
        return carMileageAmount;
    }

    public void setDailyAllowanceAmount(BigDecimal dailyAllowanceAmount) {
        this.dailyAllowanceAmount = dailyAllowanceAmount;
    }

    public void setCarMileageAmount(BigDecimal carMileageAmount) {
        this.carMileageAmount = carMileageAmount;
    }
}
