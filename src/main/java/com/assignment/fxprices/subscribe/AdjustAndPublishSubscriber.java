package com.assignment.fxprices.subscribe;

import com.assignment.fxprices.publish.AdjustedPricesPublisher;
import com.assignment.fxprices.convert.InputPriceConverter;
import com.assignment.fxprices.model.InputPrice;
import com.assignment.fxprices.model.AdjustedPrice;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 * A price message feed subscriber implementation that adjusts and publishes obtained price.
 */
@AllArgsConstructor
public class AdjustAndPublishSubscriber implements PriceMessageSubscriber {

    private static final Logger log = LoggerFactory.getLogger(AdjustAndPublishSubscriber.class);

    @NonNull
    private final InputPriceConverter inputPriceConverter;
    @NonNull
    private final UnaryOperator<BigDecimal> bidPriceAdjustment;
    @NonNull
    private final UnaryOperator<BigDecimal> askPriceAdjustment;
    @NonNull
    private final AdjustedPricesPublisher publisher;

    @Override
    public void onMessage(String message) {
        log.info("Obtained message: {}", message);
        List<InputPrice> inputPrices = inputPriceConverter.convert(message);

        List<AdjustedPrice> adjustedPrices = inputPrices.stream()
                .map(this::toAdjustedPrice)
                .collect(Collectors.toList());
        log.debug("For message \"{}\" following adjusted prices has been generated {}", message, adjustedPrices);

        log.debug("Publishing adjusted prices");
        publisher.publish(adjustedPrices);
    }

    private AdjustedPrice toAdjustedPrice(InputPrice inputPrice) {
        BigDecimal adjustedBidPrice = bidPriceAdjustment.apply(inputPrice.getBidPrice());
        BigDecimal adjustedAskPrice = askPriceAdjustment.apply(inputPrice.getAskPrice());

        return AdjustedPrice.builder()
                .inputPriceId(inputPrice.getId())
                .instrumentName(inputPrice.getInstrumentName())
                .bidPrice(adjustedBidPrice)
                .askPrice(adjustedAskPrice)
                .timestamp(inputPrice.getTimestamp())
                .build();
    }
}
