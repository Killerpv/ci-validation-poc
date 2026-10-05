package com.example.poc.rates;

import java.math.BigDecimal;

public class DiscountCalculator {
    public BigDecimal calculateDiscount(BigDecimal price, BigDecimal discountPercent) {
        if (price == null || discountPercent == null) {
            throw new IllegalArgumentException("Inputs cannot be null");
        }
        if (price.compareTo(BigDecimal.ZERO) < 0 || discountPercent.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Inputs cannot be negative");
        }
        return price.multiply(discountPercent).divide(BigDecimal.valueOf(100));
    }
}
