package com.example.poc.rates;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RateCalculator {
    public BigDecimal applyRate(BigDecimal base, BigDecimal ratePercent) {
        if (base == null || ratePercent == null) {
            throw new IllegalArgumentException("base and rate are required");
        }
        if (base.signum() < 0) {
            throw new IllegalArgumentException("base must not be negative");
        }
        return base.multiply(ratePercent).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }
}
