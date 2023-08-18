package util;

import config.RateConfig;
import model.Receipt;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReimbursementCalculatorTest {




    @Test
    void shouldCalculateTotalReimbursement() {
        //given
        int days =10;
        BigDecimal distance = BigDecimal.valueOf(120.0);
        Receipt receipt = new Receipt( 1,"plane", BigDecimal.valueOf(99.9));
        Receipt receipt1 = new Receipt( 1, "hotel", BigDecimal.valueOf(50.9));
        Receipt receipt2 = new Receipt( 1,"ticket", BigDecimal.valueOf(11.9));
        Receipt receipt3 = new Receipt( 1,"meal", BigDecimal.valueOf(12.9));
        List<Receipt> list = new ArrayList<>();
        list.add(receipt);
        list.add(receipt1);
        list.add(receipt2);
        list.add(receipt3);
        ReimbursementCalculator reimbursementCalculator = new ReimbursementCalculator();
        //then
        BigDecimal result = reimbursementCalculator.calculateTotalReimbursement(days, distance, list, RateConfig.getInstance());

        //when
        assertEquals(BigDecimal.valueOf(361.60).setScale(2, RoundingMode.CEILING), result);
    }

}