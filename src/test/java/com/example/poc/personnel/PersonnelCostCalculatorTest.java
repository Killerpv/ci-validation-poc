package com.example.poc.personnel;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.poc.rates.RateCalculator;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class PersonnelCostCalculatorTest {
    private final PersonnelCostCalculator calculator = new PersonnelCostCalculator(new RateCalculator());

    @Test
    void addsFringeToProratedSalary() {
        assertThat(calculator.personnelCost(new BigDecimal("120000"), 6, new BigDecimal("30")))
                .isEqualByComparingTo("78000.00");
    }
}
