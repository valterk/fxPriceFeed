package com.assignment.fxprices.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Represents a price definition that comes from the price feed.
 */
@AllArgsConstructor
@Builder
@Getter
@EqualsAndHashCode
@ToString
public class InputPrice {
    private final String id;
    private final InstrumentName instrumentName;
    private final BigDecimal bidPrice;
    private final BigDecimal askPrice;
    private final Instant timestamp;
}
