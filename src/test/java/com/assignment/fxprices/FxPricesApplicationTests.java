package com.assignment.fxprices;

import com.assignment.fxprices.service.AdjustedPricesService;
import com.assignment.fxprices.subscribe.PriceMessageSubscriber;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.assignment.fxprices.model.InstrumentName.EUR_JPY;
import static com.assignment.fxprices.model.InstrumentName.EUR_USD;
import static com.assignment.fxprices.model.InstrumentName.GBP_USD;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class FxPricesApplicationTests {

    @Autowired
    private AdjustedPricesService adjustedPricesService;

    @Autowired
    private PriceMessageSubscriber priceMessageSubscriber;

    @Test
    void sampleSystemTest() {
        //given
        String message1 = "106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001\n" +
                "107, EUR/JPY, 119.60,119.90,01-06-2020 12:01:02:002";
        String message2 = "108, GBP/USD, 1.2500,1.2560,01-06-2020 12:01:02:002\n" +
                "109, GBP/USD, 1.2499,1.2561,01-06-2020 12:01:02:100\n" +
                "110, EUR/JPY, 119.61,119.91,01-06-2020 12:01:02:110";

        //when
        priceMessageSubscriber.onMessage(message1);
        priceMessageSubscriber.onMessage(message2);
        var latestEUR_USD = adjustedPricesService.getLatestPrice(EUR_USD);
        var latestEUR_JPY = adjustedPricesService.getLatestPrice(EUR_JPY);
        var latestGBP_USD = adjustedPricesService.getLatestPrice(GBP_USD);

        //then
        assertThat(latestEUR_USD)
                .isPresent()
                .hasValueSatisfying(p -> {
                    assertThat(p.getInstrumentName()).isEqualTo(EUR_USD);
                    assertThat(p.getBidPrice()).isEqualByComparingTo("1.0989");
                    assertThat(p.getAskPrice()).isEqualByComparingTo("1.2012");
                });
        assertThat(latestEUR_JPY)
                .isPresent()
                .hasValueSatisfying(p -> {
                    assertThat(p.getInstrumentName()).isEqualTo(EUR_JPY);
                    assertThat(p.getBidPrice()).isEqualByComparingTo("119.49039");
                    assertThat(p.getAskPrice()).isEqualByComparingTo("120.02991");
                });
        assertThat(latestGBP_USD)
                .isPresent()
                .hasValueSatisfying(p -> {
                    assertThat(p.getInstrumentName()).isEqualTo(GBP_USD);
                    assertThat(p.getBidPrice()).isEqualByComparingTo("1.2486501");
                    assertThat(p.getAskPrice()).isEqualByComparingTo("1.2573561");
                });
    }

}
