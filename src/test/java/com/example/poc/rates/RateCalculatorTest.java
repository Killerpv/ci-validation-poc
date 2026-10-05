package com.example.poc.rates;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RateCalculatorTest {

    private RateCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new RateCalculator();
    }

    @Test
    void appliesValidRate() {
        BigDecimal result = calculator.applyRate(BigDecimal.valueOf(100), BigDecimal.valueOf(0.5));
        assertThat(result).isEqualByComparingTo("50.0");
    }

    @Test
    void rejectsNullInput() {
        assertThatThrownBy(() -> calculator.applyRate(null, BigDecimal.TEN))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void deliberatelyFailingTest() {
        assertThat(true).isFalse();
    }
}
