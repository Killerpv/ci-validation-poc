package com.example.poc.personnel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @Test
    void fullYearUsesFullSalary() {
        assertThat(calculator.personnelCost(new BigDecimal("120000"), 12, BigDecimal.ZERO))
                .isEqualByComparingTo("120000.00");
    }

    @Test
    void rejectsMonthsBelowOne() {
        assertThatThrownBy(() -> calculator.personnelCost(BigDecimal.TEN, 0, BigDecimal.TEN))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("months");
    }

    @Test
    void rejectsMonthsAboveTwelve() {
        assertThatThrownBy(() -> calculator.personnelCost(BigDecimal.TEN, 13, BigDecimal.TEN))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsMissingSalary() {
        assertThatThrownBy(() -> calculator.personnelCost(null, 6, BigDecimal.TEN))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("required");
    }
}
