package net.optionsbot.domain;

import javax.annotation.concurrent.Immutable;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

/**
 * <p>
 * This record encapsulates the key attributes of an option.
 * </p>
 *
 * @param contractId      Unique identifier for the option contract.
 * @param style           The option style (e.g., American, European).
 * @param type            The option type (CALL or PUT).
 * @param underlyingAsset The underlying asset associated with the option.
 * @param costBasis       The cost basis of the option (negative for written options, positive for bought options).
 * @param strikePrice     The strike price at which the option can be exercised.
 * @param premium         The premium (cost) paid for the option.
 * @param contractSize    The number of units per contract (e.g., 100 for equity options).
 * @param position        The number of contracts held (negative if written/sold, positive if bought).
 * @param orderDate       The date when the option was ordered.
 * @param expiryDate      The expiration date of the option.
 */
@Immutable
public record Option(
        UUID contractId,
        OptionStyle style,
        OptionType type,
        Asset underlyingAsset,
        Amount costBasis,
        Amount strikePrice,
        Amount premium,
        Integer contractSize,
        Integer position,
        LocalDate orderDate,
        LocalDate expiryDate
) {
    public Option {
        Objects.requireNonNull(contractId, "Contract ID cannot be null");
        Objects.requireNonNull(style, "Option style cannot be null");
        Objects.requireNonNull(type, "Option type cannot be null");
        Objects.requireNonNull(underlyingAsset, "Underlying asset cannot be null");
        Objects.requireNonNull(costBasis, "Cost basis cannot be null");
        Objects.requireNonNull(strikePrice, "Strike price cannot be null");
        Objects.requireNonNull(premium, "Premium cannot be null");
        Objects.requireNonNull(contractSize, "Contract size cannot be null");
        Objects.requireNonNull(position, "Position cannot be null");
        Objects.requireNonNull(orderDate, "Order date cannot be null");
        Objects.requireNonNull(expiryDate, "Expiry date cannot be null");
        if (strikePrice.isNegative()) {
            throw new IllegalArgumentException("Strike price must be non-negative");
        }
        if (premium.isNegative()) {
            throw new IllegalArgumentException("Premium must be non-negative");
        }
        if (contractSize < 1) {
            throw new IllegalArgumentException("Contract size must be positive");
        }
        if (position == 0) {
            throw new IllegalArgumentException("Position must be non-zero");
        }
        if ((position < 0 && costBasis.isPositive()) || (position > 0 && costBasis.isNegative())) {
            throw new IllegalArgumentException("Cost basis should be negative for written options and positive for bought options");
        }
        if (!expiryDate.isAfter(orderDate)) {
            throw new IllegalArgumentException("Expiry date must be after order date");
        }
    }

    public String label() {
        return String.format(
                "%s %s %s %s",
                underlyingAsset.symbol(),
                formatExpiryDate(),
                strikePrice.quantity(),
                type.label()
        );
    }

    @Override
    public String toString() {
        return contractId.toString();
    }

    /**
     * <p>
     * Formats the expiry date into the standard options market notation.
     * </p>
     *
     * Example:
     * <pre>
     *  Mar21'25
     * </pre>
     */
    private String formatExpiryDate() {
        Objects.requireNonNull(expiryDate, "Expiry date cannot be null");
        String formattedDate = "";
        formattedDate += expiryDate.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH); // "Mar" for March
        formattedDate += expiryDate.getDayOfMonth() + "'"; // Day of month
        formattedDate += expiryDate.getYear() % 100; // Last two digits of year
        return formattedDate;
    }
}
