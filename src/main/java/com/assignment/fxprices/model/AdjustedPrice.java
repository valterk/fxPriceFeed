package com.assignment.fxprices.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Represents a price that has been adjusted in the system.
 */
@Builder
@EqualsAndHashCode
@Getter
@ToString
@AllArgsConstructor
public class AdjustedPrice {
    private final String inputPriceId;
    private final InstrumentName instrumentName;
    private final BigDecimal bidPrice;
    private final BigDecimal askPrice;
    private final Instant timestamp;
}
