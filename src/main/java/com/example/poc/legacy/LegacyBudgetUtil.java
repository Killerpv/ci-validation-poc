package com.example.poc.legacy;

/** Stand-in for old, untested code. It keeps the module-wide coverage low, like the real baseline. */
public class LegacyBudgetUtil {
    public static double applyInflation(double amount, int years, double yearlyPercent) {
        double result = amount;
        for (int i = 0; i < years; i++) {
            result = result + (result * yearlyPercent / 100);
        }
        return result;
    }

    public static double capAmount(double amount, double cap) {
        if (amount > cap) {
            return cap;
        }
        return amount;
    }

    public static String label(double amount) {
        if (amount < 0) {
            return "CREDIT";
        } else if (amount == 0) {
            return "ZERO";
        }
        return "DEBIT";
    }
}
