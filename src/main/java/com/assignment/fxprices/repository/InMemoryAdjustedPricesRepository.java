package com.assignment.fxprices.repository;

import com.assignment.fxprices.model.AdjustedPrice;
import com.assignment.fxprices.model.InstrumentName;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A simple in-memory thread-safe implementation of the adjusted prices repository.
 */
public class InMemoryAdjustedPricesRepository implements AdjustedPricesRepository {

    private final ConcurrentHashMap<InstrumentName, AdjustedPrice> storageMap = new ConcurrentHashMap<>();

    @Override
    public void putAdjustedPrices(List<AdjustedPrice> adjustedPrices) {
        adjustedPrices.forEach(p -> {
            storageMap.merge(p.getInstrumentName(), p, (priceA, priceB) -> {
                if (priceB.getTimestamp().isAfter(priceA.getTimestamp())) {
                    return priceB;
                } else {
                    return priceA;
                }
            });
        });
    }

    @Override
    public Optional<AdjustedPrice> getLatestPrice(InstrumentName instrumentName) {
        return Optional.ofNullable(storageMap.get(instrumentName));
    }
}
