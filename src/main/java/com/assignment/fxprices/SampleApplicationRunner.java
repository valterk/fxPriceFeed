package com.assignment.fxprices;

import com.assignment.fxprices.model.InstrumentName;
import com.assignment.fxprices.service.AdjustedPricesService;
import com.assignment.fxprices.subscribe.PriceMessageSubscriber;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static com.assignment.fxprices.model.InstrumentName.EUR_JPY;
import static com.assignment.fxprices.model.InstrumentName.EUR_USD;
import static com.assignment.fxprices.model.InstrumentName.GBP_USD;

@Component
@AllArgsConstructor
@Profile("!test")
public class SampleApplicationRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(SampleApplicationRunner.class);

    @NonNull
    private final AdjustedPricesService adjustedPricesService;

    @NonNull
    private final PriceMessageSubscriber priceMessageSubscriber;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Messages publishing by the feed");

        priceMessageSubscriber.onMessage("106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001\n" +
                "107, EUR/JPY, 119.60,119.90,01-06-2020 12:01:02:002");
        priceMessageSubscriber.onMessage("108, GBP/USD, 1.2500,1.2560,01-06-2020 12:01:02:002\n" +
                "109, GBP/USD, 1.2499,1.2561,01-06-2020 12:01:02:100\n" +
                "110, EUR/JPY, 119.61,119.91,01-06-2020 12:01:02:110");

        log.info("Getting latest prices");
        var result = adjustedPricesService.getLatestPrice(EUR_USD);
        log.info("EUR/USD latest price: {}", result.orElseThrow());
        result = adjustedPricesService.getLatestPrice(EUR_JPY);
        log.info("EUR/JPY latest price: {}", result.orElseThrow());
        result = adjustedPricesService.getLatestPrice(GBP_USD);
        log.info("GBP/USD latest price: {}", result.orElseThrow());
    }
}
