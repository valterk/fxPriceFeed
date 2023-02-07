package com.assignment.fxprices.repository;

import com.assignment.fxprices.model.AdjustedPrice;
import com.assignment.fxprices.model.InstrumentName;

import java.util.List;
import java.util.Optional;

/**
 * A repository for storing most recently obtained adjusted prices.
 */
public interface AdjustedPricesRepository {

    /**
     * Puts list of {@link AdjustedPrice} instances to the repository.
     *
     * @param adjustedPrices a list of adjusted prices to be placed in the repository
     */
    void putAdjustedPrices(List<AdjustedPrice> adjustedPrices);

    /**
     * Gets the latest adjusted price obtained from the feed.
     *
     * @param instrumentName name of the instrument
     * @return Optional of {@link AdjustedPrice} instance if present, empty otherwise
     */
    Optional<AdjustedPrice> getLatestPrice(InstrumentName instrumentName);
}
