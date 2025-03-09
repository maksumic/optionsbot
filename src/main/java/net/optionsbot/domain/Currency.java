package net.optionsbot.domain;

import java.util.Locale;

public enum Currency {
    EUR(2, Locale.GERMANY),
    GBP(2, Locale.UK),
    AUD(2, Locale.of("en", "AU")),
    NZD(2, Locale.of("en", "NZ")),
    USD(2, Locale.US),
    CAD(2, Locale.of("en", "CA")),
    CHF(2, Locale.of("de", "CH")),
    JPY(0, Locale.JAPAN),
    CNH(2, Locale.CHINA), // Chinese offshore
    CNY(2, Locale.CHINA), // Chinese onshore
    INR(2, Locale.of("en", "IN")),
    KWD(3, Locale.of("ar", "KW")),
    ;

    private final int minorUnit;
    private final Locale locale;

    Currency(int minorUnit, Locale locale) {
        this.minorUnit = minorUnit;
        this.locale = locale;
    }

    public int minorUnit() {
        return minorUnit;
    }

    public Locale locale() {
        return locale;
    }
}
