package config;

import model.Type;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class RateConfig {
    private static volatile RateConfig instance;

    private BigDecimal dailyAllowanceAmount;
    private BigDecimal carMileageAmount;
    private BigDecimal carMileageLimit;

    private final EnumMap<Type, BigDecimal> receiptTypeLimits = new EnumMap<>(Type.class);

    public static RateConfig getInstance() {
        if (instance == null) {
            synchronized (RateConfig.class) {
                if (instance == null) {
                    instance = new RateConfig();
                }
            }
        }
        return instance;
    }

    private RateConfig() {
        initializeDefaultLimits();
    }

    private void initializeDefaultLimits() {
        dailyAllowanceAmount = BigDecimal.valueOf(15);
        carMileageAmount = BigDecimal.valueOf(0.3);
        carMileageLimit = BigDecimal.valueOf(100000);
        receiptTypeLimits.put(Type.TAXI, BigDecimal.valueOf(50.00));
        receiptTypeLimits.put(Type.HOTEL, BigDecimal.valueOf(200.00));
        receiptTypeLimits.put(Type.PLANE_TICKET, BigDecimal.valueOf(500.00));
        receiptTypeLimits.put(Type.TRAIN, BigDecimal.valueOf(75.00));
        receiptTypeLimits.put(Type.FOOD, BigDecimal.valueOf(100.00));
        receiptTypeLimits.put(Type.RELAX, BigDecimal.valueOf(100.00));
        receiptTypeLimits.put(Type.TICKET, BigDecimal.valueOf(50.00));
    }

    public BigDecimal getCarMileageLimit() {
        return carMileageLimit;
    }

    public void setCarMileageLimit(BigDecimal carMileageLimit) {
        this.carMileageLimit = carMileageLimit;
    }

    public Map<Type, BigDecimal> getReceiptTypeLimits() {
        return Collections.unmodifiableMap(receiptTypeLimits);
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
        StringBuilder builder = new StringBuilder();
        builder.append("RateConfig{");
        builder.append("dailyAllowanceAmount=").append(dailyAllowanceAmount);
        builder.append(", carMileageAmount=").append(carMileageAmount);
        builder.append(", carMileageLimit=").append(carMileageLimit);

        for (Map.Entry<Type, BigDecimal> entry : receiptTypeLimits.entrySet()) {
            builder.append(", receiptTypeLimits(").append(entry.getKey()).append(")=").append(entry.getValue());
        }

        builder.append('}');
        return builder.toString();
    }
}