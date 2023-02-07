package com.assignment.fxprices.publish;

import com.assignment.fxprices.model.AdjustedPrice;
import com.assignment.fxprices.repository.AdjustedPricesRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.List;

/**
 * Adjusted prices published implementation that stores them in specified local repository.
 */
@AllArgsConstructor
public class LocalRepositoryPublisher implements AdjustedPricesPublisher {

    @NonNull
    private final AdjustedPricesRepository repository;

    @Override
    public void publish(List<AdjustedPrice> adjustedPrices) {
        repository.putAdjustedPrices(adjustedPrices);
    }
}
