package util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import config.RateConfig;
import model.Receipt;
import model.Type;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class ReceiptValidatorTest {


    @Test
    void shouldCheckPriceIsGreaterThanOrEquals0() {
        //given
        ReceiptValidator receiptValidator = new ReceiptValidator();
        Receipt receipt1 = new Receipt(1, Type.HOTEL,BigDecimal.valueOf(0));
        Receipt receipt2 = new Receipt(1, Type.FOOD,BigDecimal.valueOf(-600));
        Receipt receipt3 = new Receipt(1, Type.RELAX,BigDecimal.valueOf(200));
        Receipt receipt4 = new Receipt(1, Type.PLANE_TICKET,BigDecimal.valueOf(-150));

        //when & then
        assertTrue(receiptValidator.isValidAmount(receipt1));
        assertFalse(receiptValidator.isValidAmount(receipt2));
        assertTrue(receiptValidator.isValidAmount(receipt3));
        assertFalse(receiptValidator.isValidAmount(receipt4));

    }

    @Test
    void shouldCheckPrice() {
        ReceiptValidator receiptValidator = new ReceiptValidator();
        Receipt receipt1 = new Receipt(1, Type.HOTEL,BigDecimal.valueOf(0));
        Receipt receipt2 = new Receipt(1, Type.FOOD,BigDecimal.valueOf(600));
        Receipt receipt3 = new Receipt(1, Type.RELAX,BigDecimal.valueOf(200));
        Receipt receipt4 = new Receipt(1, Type.PLANE_TICKET,BigDecimal.valueOf(-150));

        assertTrue(receiptValidator.isWithinLimits(RateConfig.getInstance(), receipt1));
        assertFalse(receiptValidator.isWithinLimits(RateConfig.getInstance(), receipt2));
        assertFalse(receiptValidator.isWithinLimits(RateConfig.getInstance(), receipt3));
        assertFalse(receiptValidator.isWithinLimits(RateConfig.getInstance(), receipt4));

    }

}

