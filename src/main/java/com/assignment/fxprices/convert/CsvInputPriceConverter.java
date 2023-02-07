package com.assignment.fxprices.convert;

import com.assignment.fxprices.model.InputPrice;
import com.assignment.fxprices.model.InstrumentName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * Input price converter implementation that converts from CSV string.
 */
public class CsvInputPriceConverter implements InputPriceConverter {

    private static final Logger log = LoggerFactory.getLogger(CsvInputPriceConverter.class);

    private static final int PRICE_PARAMETERS_COUNT = 5;
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss:SSS");

    @Override
    public List<InputPrice> convert(String value) {
        log.debug("Converting for value {}", value);
        if (value == null || value.isEmpty()) {
            log.debug("Value is null or empty, returning an empty list");
            return List.of();
        }

        return value.lines()
                .map(this::convertPrice)
                .collect(Collectors.toList());
    }

    private InputPrice convertPrice(String value) {
        log.debug("Converting a single price from {}", value);
        String[] parameters = value.split(",");

        if (parameters.length != PRICE_PARAMETERS_COUNT) {
            log.debug("Wrong number of price parameters, throwing exception");
            throw new IllegalArgumentException(format("Single price definition should contain %s comma separated parameters",
                    PRICE_PARAMETERS_COUNT));
        }

        return InputPrice.builder()
                .id(parseId(parameters[0]))
                .instrumentName(parseInstrumentName(parameters[1]))
                .bidPrice(parseAmount(parameters[2]))
                .askPrice(parseAmount(parameters[3]))
                .timestamp(parseTimestamp(parameters[4]))
                .build();
    }

    private String parseId(String value) {
        return verifyNotEmpty(value.trim(), "Id");
    }

    private InstrumentName parseInstrumentName(String value) {
        return InstrumentName.valueOfLabel(value.trim())
                .orElseThrow(() -> new IllegalArgumentException(format("Unexpected instrument name: %s", value)));
    }

    private BigDecimal parseAmount(String value) {
        BigDecimal parsed = new BigDecimal(value.trim());
        if (parsed.signum() <= 0) {
            throw new IllegalArgumentException("Prices should be greater than 0");
        }
        return parsed;
    }

    private Instant parseTimestamp(String value) {
        try {
            return LocalDateTime
                    .parse(value.trim(), TIMESTAMP_FORMATTER)
                    //assuming that all timestamps are in UTC
                    .toInstant(ZoneOffset.UTC);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException(format("Incorrect format of the timestamp %s", value), ex);
        }
    }

    private String verifyNotEmpty(String value, String name) {
        if (value.isEmpty()) {
            throw new IllegalArgumentException(format("%s should not be empty", name));
        }
        return value;
    }
}
