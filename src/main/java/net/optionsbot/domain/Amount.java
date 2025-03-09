package net.optionsbot.domain;

import javax.annotation.concurrent.Immutable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Immutable
public record Amount(Currency currency, BigDecimal quantity) {
    private static final Pattern AMOUNT_PATTERN = Pattern.compile("([A-Z]{3,4})\\s(-?\\d+(?:[,'\\s]\\d+)*(?:\\.\\d+)?)");

    public Amount {
        Objects.requireNonNull(currency, "Currency cannot be null.");
        Objects.requireNonNull(quantity, "Quantity cannot be null.");
    }

    public static Amount parse(String string) {
        Objects.requireNonNull(string, "Failed to parse string to amount: null");
        Matcher matcher = AMOUNT_PATTERN.matcher(string);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Failed to convert string to amount: invalid format (example: EUR 10,000.00)");
        }
        String currencyCode = matcher.group(1);
        String numericValue = matcher.group(2);
        Currency currency = Currency.valueOf(currencyCode);
        BigDecimal quantity = parseLocalizedAmount(numericValue, currency);
        quantity = quantity.setScale(currency.minorUnit(), RoundingMode.HALF_EVEN);
        return new Amount(currency, quantity);
    }

    public boolean isPositive() {
        return quantity.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isNegative() {
        return quantity.compareTo(BigDecimal.ZERO) < 0;
    }

    public boolean isZero() {
        return quantity.compareTo(BigDecimal.ZERO) == 0;
    }

    @Override
    public String toString() {
        NumberFormat formatter = NumberFormat.getNumberInstance(currency.locale());
        formatter.setMinimumFractionDigits(currency.minorUnit());
        formatter.setMaximumFractionDigits(currency.minorUnit());
        formatter.setGroupingUsed(true);
        BigDecimal scaledQuantity = quantity.setScale(currency.minorUnit(), RoundingMode.HALF_EVEN);
        return currency + " " + formatter.format(scaledQuantity);
    }

    private static BigDecimal parseLocalizedAmount(String numericValue, Currency currency) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
        formatter.setParseBigDecimal(true);
        formatter.setGroupingUsed(true);
        formatter.setRoundingMode(RoundingMode.HALF_EVEN);
        try {
            BigDecimal result = (BigDecimal) formatter.parseObject(numericValue);
            if (result == null) {
                throw new IllegalArgumentException("Failed to parse amount: " + numericValue);
            }
            return result.setScale(currency.minorUnit(), RoundingMode.HALF_EVEN);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Failed to parse amount: " + numericValue, e);
        }
    }
}