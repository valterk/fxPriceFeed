package com.assignment.fxprices.function;

import java.math.BigDecimal;
import java.util.function.UnaryOperator;

/**
 * Function that returns value increased by a percentage value.
 */
public class PercentageIncreaseFunction implements UnaryOperator<BigDecimal> {

    private static final BigDecimal PERCENTAGE_DIVIDE_FACTOR = new BigDecimal("100");

    private final BigDecimal factor;

    /**
     * Creates the function returned the source value increased by its specified percentage value.
     *
     * @param percentage percentage value, for instance for 1.5%, use 1.5 double value, it should be a non-negative
     * value
     */
    public PercentageIncreaseFunction(double percentage) {
        BigDecimal percentageBigDecimal = BigDecimal.valueOf(percentage);
        if (percentageBigDecimal.signum() < 0) {
            throw new IllegalArgumentException("Percentage should be a non-negative value");
        }
        factor = percentageBigDecimal.divide(PERCENTAGE_DIVIDE_FACTOR);
    }

    @Override
    public BigDecimal apply(BigDecimal source) {
        return source.add(source.multiply(factor));
    }
}
