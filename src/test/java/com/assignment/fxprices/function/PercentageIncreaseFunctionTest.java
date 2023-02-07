package com.assignment.fxprices.function;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PercentageIncreaseFunctionTest {

    @Test
    void forNegativePercentageValueFunctionThrowsException() {
        //then
        assertThrows(IllegalArgumentException.class, () -> new PercentageIncreaseFunction(-1));
    }

    @Test
    void functionCalculatesCorrectValueForSpecifiedPercentage() {
        //given
        BigDecimal source = new BigDecimal("100.00");
        PercentageIncreaseFunction function = new PercentageIncreaseFunction(5);

        //when
        BigDecimal result = function.apply(source);

        //then
        assertThat(result).isEqualByComparingTo("105.00");
    }

    @Test
    void forZeroPercentageFunctionReturnsTheSameValue() {
        //given
        BigDecimal source = new BigDecimal("100.00");
        PercentageIncreaseFunction function = new PercentageIncreaseFunction(0);

        //when
        BigDecimal result = function.apply(source);

        //then
        assertThat(result).isEqualByComparingTo(source);
    }

    @Test
    void functionCalculatesCorrectValueForPercentageLowerThanOne() {
        //given
        BigDecimal source = new BigDecimal("100.00");
        PercentageIncreaseFunction function = new PercentageIncreaseFunction(0.1);

        //when
        BigDecimal result = function.apply(source);

        //then
        assertThat(result).isEqualByComparingTo("100.10");
    }

    @Test
    void functionCalculatesCorrectValueForPercentageHigherThanOneHundred() {
        //given
        BigDecimal source = new BigDecimal("100.00");
        PercentageIncreaseFunction function = new PercentageIncreaseFunction(150);

        //when
        BigDecimal result = function.apply(source);

        //then
        assertThat(result).isEqualByComparingTo("250.00");
    }
}