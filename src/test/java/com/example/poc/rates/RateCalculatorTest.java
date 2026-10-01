package com.example.poc.rates;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class RateCalculatorTest {
    private final RateCalculator calculator = new RateCalculator();

    @Test
    void appliesPercentageRate() {
        assertThat(calculator.applyRate(new BigDecimal("1000"), new BigDecimal("25")))
                .isEqualByComparingTo("250.00");
    }

    @Test
    void zeroBaseGivesZero() {
        assertThat(calculator.applyRate(BigDecimal.ZERO, new BigDecimal("25"))).isEqualByComparingTo("0.00");
    }

    @Test
    void rejectsNegativeBase() {
        assertThatThrownBy(() -> calculator.applyRate(new BigDecimal("-1"), BigDecimal.TEN))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsNullInput() {
        assertThatThrownBy(() -> calculator.applyRate(null, BigDecimal.TEN))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
