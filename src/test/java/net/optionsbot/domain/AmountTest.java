package net.optionsbot.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AmountTest {
    @ParameterizedTest
    @CsvSource(value = {
            "EUR 1 | EUR | 1.00",
            "USD 10.50 | USD | 10.50",
            "JPY 1000 | JPY | 1000",
            "GBP 1,234.56 | GBP | 1234.56",
            "AUD 0.99 | AUD | 0.99",
            "CAD 100,000.00 | CAD | 100000.00",
            "EUR -1 | EUR | -1.00",
            "USD -10.50 | USD | -10.50",
            "JPY -1000 | JPY | -1000",
            "EUR 0 | EUR | 0.00",
            "USD 0.00 | USD | 0.00",
            "INR 12,34,567.89 | INR | 1234567.89",
            "INR 1,23,456.78 | INR | 123456.78",
            "CNY 1,234,567.89 | CNY | 1234567.89",
            "CNY 12,345.67 | CNY | 12345.67",
            "USD 1,234,567.89 | USD | 1234567.89",
            "JPY 1,234,567 | JPY | 1234567",
            "KWD 123.456 | KWD | 123.456",
            "KWD 1,234.567 | KWD | 1234.567"
    }, delimiter = '|')
    public void testParseValidInputs(String input, String currency, BigDecimal quantity) {
        Amount amount = Amount.parse(input);
        Assertions.assertEquals(Currency.valueOf(currency), amount.currency(), "Currency mismatch for input: " + input);
        Assertions.assertEquals(quantity, amount.quantity(), "Quantity mismatch for input: " + input);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "' '",          // Empty string
            "EUR",          // Missing quantity
            "100.00",       // Missing currency
            "USD ABC",      // Non-numeric amount
            "EUR 1.2.3",    // Multiple decimal points
            "USD 1.000.00", // Incorrect thousands separator
//          "EUR 10,00",    // Incorrect separator
    }, delimiter = '|')
    public void testParseInvalidInputsThrowsException(String input) {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> Amount.parse(input));
        Assertions.assertEquals("Failed to convert string to amount: invalid format (example: EUR 10,000.00)", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "-1, true",
            "0, false",
            "1, false",
    })
    public void testIsNegative(BigDecimal input, boolean expected) {
        Amount amount = new Amount(Currency.EUR, input);
        assertEquals(expected, amount.isNegative(), "isNegative() failed for input: " + input);
    }

    @ParameterizedTest
    @CsvSource({
            "1, true",
            "0, false",
            "-1, false",
    })
    public void testIsPositive(BigDecimal input, boolean expected) {
        Amount amount = new Amount(Currency.EUR, input);
        assertEquals(expected, amount.isPositive(), "isPositive() failed for input: " + input);
    }

    @ParameterizedTest
    @CsvSource({
            "0, true",
            "1, false",
            "-1, false",
    })
    public void testIsZero(BigDecimal input, boolean expected) {
        Amount amount = new Amount(Currency.EUR, input);
        assertEquals(expected, amount.isZero(), "isZero() failed for input: " + input);
    }
}
