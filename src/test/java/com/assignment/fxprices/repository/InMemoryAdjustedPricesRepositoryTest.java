package com.assignment.fxprices.repository;

import com.assignment.fxprices.model.AdjustedPrice;
import com.assignment.fxprices.model.InstrumentName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static com.assignment.fxprices.model.InstrumentName.EUR_JPY;
import static com.assignment.fxprices.model.InstrumentName.EUR_USD;
import static com.assignment.fxprices.model.InstrumentName.GBP_USD;
import static org.assertj.core.api.Assertions.assertThat;

class InMemoryAdjustedPricesRepositoryTest {

    private static final AdjustedPrice PRICE_1 = new AdjustedPrice("id1", EUR_USD,
            new BigDecimal("1.0"), new BigDecimal("1.1"), Instant.now().minusSeconds(10));
    private static final AdjustedPrice PRICE_1_NEWER = new AdjustedPrice("id1_2", EUR_USD,
            new BigDecimal("1.2"), new BigDecimal("1.3"), Instant.now());
    private static final AdjustedPrice PRICE_2 = new AdjustedPrice("id2", EUR_JPY,
            new BigDecimal("2.0"), new BigDecimal("2.1"), Instant.now().minusSeconds(10));
    private static final AdjustedPrice PRICE_2_OLDER = new AdjustedPrice("id2_2", EUR_JPY,
            new BigDecimal("2.2"), new BigDecimal("2.3"), Instant.now().minusSeconds(20));
    private static final AdjustedPrice PRICE_3 = new AdjustedPrice("id3", GBP_USD,
            new BigDecimal("3.0"), new BigDecimal("3.1"), Instant.now().minusSeconds(10));

    private InMemoryAdjustedPricesRepository repository;

    @BeforeEach
    void init() {
        repository = new InMemoryAdjustedPricesRepository();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "EUR_USD",
            "GBP_USD",
            "EUR_JPY"})
    void forEmptyRepositoryGetLatestPriceForAllInstrumentsReturnEmptyOptional(String instrumentNameString) {
        //given
        InstrumentName instrumentName = InstrumentName.valueOf(instrumentNameString);

        //when
        var result = repository.getLatestPrice(instrumentName);

        //then
        assertThat(result).isEmpty();
    }

    @Test
    void allPutPricesCanBeAccessedByGetLatestPrice() {
        //given
        repository.putAdjustedPrices(List.of(PRICE_1, PRICE_2, PRICE_3));

        //when
        var latestEurUsd = repository.getLatestPrice(EUR_USD);
        var latestEurJpy = repository.getLatestPrice(EUR_JPY);
        var latestGbpUsd = repository.getLatestPrice(GBP_USD);

        //then
        assertThat(latestEurUsd).isPresent().hasValue(PRICE_1);
        assertThat(latestEurJpy).isPresent().hasValue(PRICE_2);
        assertThat(latestGbpUsd).isPresent().hasValue(PRICE_3);
    }

    @Test
    void getLatestPriceReturnsOnlyNewerValuesThatHasBeenPut() {
        //given
        repository.putAdjustedPrices(List.of(PRICE_1, PRICE_2, PRICE_3));
        repository.putAdjustedPrices(List.of(PRICE_1_NEWER, PRICE_2_OLDER));

        //when
        var latestEurUsd = repository.getLatestPrice(EUR_USD);
        var latestEurJpy = repository.getLatestPrice(EUR_JPY);
        var latestGbpUsd = repository.getLatestPrice(GBP_USD);

        //then
        assertThat(latestEurUsd).isPresent().hasValue(PRICE_1_NEWER);
        assertThat(latestEurJpy).isPresent().hasValue(PRICE_2);
        assertThat(latestGbpUsd).isPresent().hasValue(PRICE_3);
    }
}