package com.assignment.fxprices.config;

import com.assignment.fxprices.convert.CsvInputPriceConverter;
import com.assignment.fxprices.function.PercentageDecreaseFunction;
import com.assignment.fxprices.function.PercentageIncreaseFunction;
import com.assignment.fxprices.publish.LocalRepositoryPublisher;
import com.assignment.fxprices.repository.AdjustedPricesRepository;
import com.assignment.fxprices.repository.InMemoryAdjustedPricesRepository;
import com.assignment.fxprices.subscribe.AdjustAndPublishSubscriber;
import com.assignment.fxprices.subscribe.PriceMessageSubscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public AdjustedPricesRepository adjustedPricesRepository() {
        return new InMemoryAdjustedPricesRepository();
    }

    @Bean
    public PriceMessageSubscriber priceMessageSubscriber() {
        return new AdjustAndPublishSubscriber(
                new CsvInputPriceConverter(),
                new PercentageDecreaseFunction(0.1),
                new PercentageIncreaseFunction(0.1),
                new LocalRepositoryPublisher(adjustedPricesRepository()));
    }
}
