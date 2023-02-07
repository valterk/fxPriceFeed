package com.assignment.fxprices.function;

import java.math.BigDecimal;
import java.util.function.UnaryOperator;

/**
 * Function that returns value decreased by a percentage value.
 */
public class PercentageDecreaseFunction  implements UnaryOperator<BigDecimal> {

    private static final BigDecimal PERCENTAGE_DIVIDE_FACTOR = new BigDecimal("100");

    private final BigDecimal factor;

    /**
     * Creates the function returned the source value decreased by its specified percentage value.
     *
     * @param percentage percentage value, for instance for 1.5%, use 1.5 double value, it should be a non-negative
     * and less or equal than 100
     * value
     */
    public PercentageDecreaseFunction(double percentage) {
        BigDecimal percentageBigDecimal = BigDecimal.valueOf(percentage);
        if (percentageBigDecimal.signum() < 0) {
            throw new IllegalArgumentException("Percentage should be a non-negative value");
        }
        if (percentageBigDecimal.compareTo(new BigDecimal("100")) > 0) {
            throw new IllegalArgumentException("Percentage should not be greater than 100");
        }
        factor = percentageBigDecimal.divide(PERCENTAGE_DIVIDE_FACTOR);
    }

    @Override
    public BigDecimal apply(BigDecimal source) {
        return source.subtract(source.multiply(factor));
    }
}
