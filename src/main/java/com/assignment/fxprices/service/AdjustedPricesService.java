package com.assignment.fxprices.service;

import com.assignment.fxprices.model.AdjustedPrice;
import com.assignment.fxprices.model.InstrumentName;
import com.assignment.fxprices.repository.AdjustedPricesRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AdjustedPricesService {

    @NonNull
    private final AdjustedPricesRepository repository;

    public Optional<AdjustedPrice> getLatestPrice(InstrumentName instrumentName) {
        return repository.getLatestPrice(instrumentName);
    }
}
