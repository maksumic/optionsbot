package net.optionsbot.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

class OptionTest {
    @Test
    void testLabel() {
        String expectedLabel = "SCHD Mar21'25 28 Put";
        String actualLabel = new Option(
                UUID.randomUUID(),
                OptionStyle.AMERICAN,
                OptionType.PUT,
                new Asset("SCHWAB US DVD EQUITY ETF ARCA", "SCHD"),
                Amount.parse("USD 30"),
                Amount.parse("USD 28"),
                Amount.parse("USD 0.20"),
                100,
                1,
                LocalDate.parse("2025-03-01"),
                LocalDate.parse("2025-03-21")
        ).label();
        Assertions.assertEquals(expectedLabel, actualLabel, "Label mismatch");
    }
}

