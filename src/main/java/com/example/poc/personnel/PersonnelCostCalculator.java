package com.example.poc.personnel;

import com.example.poc.rates.RateCalculator;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class PersonnelCostCalculator {
    private final RateCalculator rateCalculator;

    public PersonnelCostCalculator(RateCalculator rateCalculator) {
        this.rateCalculator = rateCalculator;
    }

    public BigDecimal personnelCost(BigDecimal annualSalary, int months, BigDecimal fringeRatePercent) {
        if (annualSalary == null || fringeRatePercent == null) {
            throw new IllegalArgumentException("salary and fringe rate are required");
        }
        if (months < 1 || months > 12) {
            throw new IllegalArgumentException("months must be between 1 and 12");
        }
        BigDecimal base = annualSalary.multiply(BigDecimal.valueOf(months))
                .divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP);
        BigDecimal fringe = rateCalculator.applyRate(base, fringeRatePercent);
        return base.add(fringe);
    }
}
