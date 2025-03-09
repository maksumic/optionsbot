package net.optionsbot.domain;

/**
 * Defines the exercise style of an option, which determines when the holder
 * can exercise their right to buy or sell the underlying asset.
 */
public enum OptionStyle {
    /**
     * An American-style option can be exercised at any time before or on the expiration date.
     */
    AMERICAN("American"),

    /**
     * A European-style option can only be exercised on the expiration date.
     */
    EUROPEAN("European"),
    ;

    private final String label;

    OptionStyle(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }
}
