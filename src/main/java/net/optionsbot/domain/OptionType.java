package net.optionsbot.domain;

public enum OptionType {
    /**
     * A call option grants the holder the right, but not the obligation,
     * to purchase the underlying asset at a specified strike price before expiration.
     *
     * @see OptionStyle for details on exercise mechanisms.
     */
    CALL("Call"),

    /**
     * A put option grants the holder the right, but not the obligation,
     * to sell the underlying asset at a specified strike price before expiration.
     *
     * @see OptionStyle for details on exercise mechanisms.
     */
    PUT("Put"),
    ;

    private final String label;

    OptionType(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }
}
