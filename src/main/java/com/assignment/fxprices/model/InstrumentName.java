package com.assignment.fxprices.model;

import lombok.AllArgsConstructor;

import java.util.Optional;

/**
 * Possible instrument names in the system.
 */
@AllArgsConstructor
public enum InstrumentName {
    EUR_USD("EUR/USD"),
    GBP_USD("GBP/USD"),
    EUR_JPY("EUR/JPY");

    private final String label;

    /**
     * Returns the instrument name matching specified label. Empty optional if there is no such instrument.
     *
     * @param label label of the instrument
     * @return optional with matching instrument name
     */
    public static Optional<InstrumentName> valueOfLabel(String label) {
        for (InstrumentName e : values()) {
            if (e.label.equals(label)) {
                return Optional.of(e);
            }
        }
        return Optional.empty();
    }
}
