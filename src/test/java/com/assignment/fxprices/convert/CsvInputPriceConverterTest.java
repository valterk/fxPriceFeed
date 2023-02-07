package com.assignment.fxprices.convert;

import com.assignment.fxprices.model.InputPrice;
import com.assignment.fxprices.model.InstrumentName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.StringJoiner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CsvInputPriceConverterTest {

    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss:SSS");
    private static final String PRICE_1_STRING = "106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001";
    private static final InputPrice PRICE_1 = new InputPrice("106", InstrumentName.EUR_USD, new BigDecimal("1.1000"),
            new BigDecimal("1.2000"), toInstant("01-06-2020 12:01:01:001"));
    private static final String PRICE_2_STRING = "107, EUR/JPY, 119.60,119.90,01-06-2020 12:01:02:002";
    private static final InputPrice PRICE_2 = new InputPrice("107", InstrumentName.EUR_JPY, new BigDecimal("119.60"),
            new BigDecimal("119.90"), toInstant("01-06-2020 12:01:02:002"));
    private static final String PRICE_3_STRING = "108, GBP/USD, 1.2500,1.2560,01-06-2020 12:01:02:002";
    private static final InputPrice PRICE_3 = new InputPrice("108", InstrumentName.GBP_USD, new BigDecimal("1.2500"),
            new BigDecimal("1.2560"), toInstant("01-06-2020 12:01:02:002"));

    CsvInputPriceConverter converter = new CsvInputPriceConverter();

    @Test
    void forNullValueEmptyListShouldBeReturned() {
        //when
        List<InputPrice> result = converter.convert(null);

        //then
        assertThat(result)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void forEmptyValueEmptyListShouldBeReturned() {
        //when
        List<InputPrice> result = converter.convert("");

        //then
        assertThat(result)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void convertsSinglePriceCorrectly() {
        //when
        List<InputPrice> result = converter.convert(PRICE_1_STRING);

        //then
        assertThat(result)
                .containsExactly(PRICE_1);
    }

    @Test
    void convertsMultiplePricesCorrectly() {
        //given
        String input = new StringJoiner("\n")
                .add(PRICE_1_STRING)
                .add(PRICE_2_STRING)
                .add(PRICE_3_STRING)
                .toString();

        //when
        List<InputPrice> result = converter.convert(input);

        //then
        assertThat(result)
                .containsExactly(PRICE_1, PRICE_2, PRICE_3);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "106, 1.1000,1.2000,01-06-2020 12:01:01:001",
            "106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001, more",
            ", EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001",
            "106,, 1.1000,1.2000,01-06-2020 12:01:01:001",
            "106, EUR/USD,,1.2000,01-06-2020 12:01:01:001",
            "106, EUR/USD, 1.1000,,01-06-2020 12:01:01:001",
            "106, EUR/USD, 1.1000,1.2000,",
            "106, EUR/PLN, 1.1000,1.2000,01-06-2020 12:01:01:001",
            "106, EUR/USD, NaN,1.2000,01-06-2020 12:01:01:001",
            "106, EUR/USD, 1.1000,NaN,01-06-2020 12:01:01:001",
            "106, EUR/USD, -1.1000,1.2000,01-06-2020 12:01:01:001",
            "106, EUR/USD, 1.1000,0,01-06-2020 12:01:01:001",
            "106, EUR/USD, 1.1000,1.2000,wrongDate"})
    void forIncorrectInputIllegalArgumentExceptionShouldBeThrown(String input) {
        //then
        assertThrows(IllegalArgumentException.class, () -> converter.convert(input));
    }

    private static Instant toInstant(String timestamp) {
        return LocalDateTime.parse(timestamp, TIMESTAMP_FORMATTER)
                .toInstant(ZoneOffset.UTC);
    }
}