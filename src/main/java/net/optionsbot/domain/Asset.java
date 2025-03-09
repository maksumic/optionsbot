package net.optionsbot.domain;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Asset(String fullName, String symbol) {
    private static final Pattern SYMBOL_PATTERN = Pattern.compile("^[A-Z]{1,8}$");

    public Asset {
        Objects.requireNonNull(fullName, "Full name cannot be null");
        Objects.requireNonNull(symbol, "Symbol cannot be null");
        Matcher matcher = SYMBOL_PATTERN.matcher(symbol);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid symbol: does not match pattern: " + SYMBOL_PATTERN.pattern());
        }
    }
}
