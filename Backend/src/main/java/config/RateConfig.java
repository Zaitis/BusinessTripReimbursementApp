package config;

import model.Type;

import java.math.BigDecimal;
import java.util.EnumMap;

public class RateConfig {
    private static RateConfig instance;
    private BigDecimal dailyAllowanceAmount;
    private BigDecimal carMileageAmount;
    private BigDecimal carMileageLimit;
    private EnumMap<Type, BigDecimal> receiptTypeLimits = new EnumMap<>(Type.class);

    public static RateConfig getInstance() {
        if (instance == null) {
            instance = new RateConfig();
        }
        return instance;
    }

    private RateConfig() {
        initializeDefaultLimits();
    }

    private void initializeDefaultLimits() {
        dailyAllowanceAmount = new BigDecimal(15);
        carMileageAmount = new BigDecimal(0.3);
        carMileageLimit= new BigDecimal(100000);
        receiptTypeLimits.put(Type.TAXI, new BigDecimal("50.00"));
        receiptTypeLimits.put(Type.HOTEL, new BigDecimal("200.00"));
        receiptTypeLimits.put(Type.PLANE_TICKET, new BigDecimal("500.00"));
        receiptTypeLimits.put(Type.TRAIN, new BigDecimal("75.00"));
        receiptTypeLimits.put(Type.FOOD, new BigDecimal("100.00"));
        receiptTypeLimits.put(Type.RELAX, new BigDecimal("100.00"));
        receiptTypeLimits.put(Type.TICKET, new BigDecimal("50.00"));
    }

    public BigDecimal getCarMileageLimit() {
        return carMileageLimit;
    }

    public void setCarMileageLimit(BigDecimal carMileageLimit) {
        this.carMileageLimit = carMileageLimit;
    }

    public EnumMap<Type, BigDecimal> getReceiptTypeLimits() {
        return receiptTypeLimits;
    }

    public BigDecimal getLimitForType(Type type) {
        return receiptTypeLimits.get(type);
    }

    public void setLimitForType(Type type, BigDecimal limit) {
        receiptTypeLimits.put(type, limit);
    }

    public BigDecimal getDailyAllowanceAmount() {
        return dailyAllowanceAmount;
    }

    public void setDailyAllowanceAmount(BigDecimal dailyAllowanceAmount) {
        this.dailyAllowanceAmount = dailyAllowanceAmount;
    }

    public BigDecimal getCarMileageAmount() {
        return carMileageAmount;
    }

    public void setCarMileageAmount(BigDecimal carMileageAmount) {
        this.carMileageAmount = carMileageAmount;
    }

    @Override
    public String toString() {
        return "RateConfig{" +
                "dailyAllowanceAmount=" + dailyAllowanceAmount +
                ", carMileageAmount=" + carMileageAmount +
                ", carMileageLimit=" + carMileageLimit +
                ", receiptTypeLimits=" + receiptTypeLimits.get(Type.TAXI) +
                ", receiptTypeLimits=" + receiptTypeLimits.get(Type.HOTEL) +
                ", receiptTypeLimits=" + receiptTypeLimits.get(Type.PLANE_TICKET) +
                ", receiptTypeLimits=" + receiptTypeLimits.get(Type.TRAIN) +
                ", receiptTypeLimits=" + receiptTypeLimits.get(Type.FOOD) +
                ", receiptTypeLimits=" + receiptTypeLimits.get(Type.RELAX) +
                ", receiptTypeLimits=" + receiptTypeLimits.get(Type.TICKET) +
                '}';
    }
}
