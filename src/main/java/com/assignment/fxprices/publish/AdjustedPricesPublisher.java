package com.assignment.fxprices.publish;

import com.assignment.fxprices.model.AdjustedPrice;

import java.util.List;

/**
 * Published of instances of {@link AdjustedPrice} obtained from the feed.
 */
public interface AdjustedPricesPublisher {

    /**
     * Publishes specified adjusted prices.
     *
     * @param adjustedPrices list of adjusted prices to be published
     */
    void publish(List<AdjustedPrice> adjustedPrices);
}
