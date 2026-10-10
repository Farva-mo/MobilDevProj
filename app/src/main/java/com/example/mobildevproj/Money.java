package com.example.mobildevproj;

import java.util.Locale;

/** Formats dollar amounts for display. */
public final class Money {

    private Money() {
    }

    /** 25 -> "$25.00" */
    public static String plain(double amount) {
        return String.format(Locale.US, "$%.2f", Math.abs(amount));
    }

    /** 62.5 -> "+$62.50", -24 -> "-$24.00", 0 -> "$0.00" */
    public static String signed(double amount) {
        if (amount > 0) {
            return "+" + plain(amount);
        }
        if (amount < 0) {
            return "-" + plain(amount);
        }
        return plain(0);
    }
}
