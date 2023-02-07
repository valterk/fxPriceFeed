package com.assignment.fxprices.function;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PercentageDecreaseFunctionTest {

    @ParameterizedTest
    @ValueSource(doubles = { -1, 150})
    void forIncorrectPercentageValueFunctionThrowsException(double value) {
        //then
        assertThrows(IllegalArgumentException.class, () -> new PercentageDecreaseFunction(value));
    }

    @Test
    void functionCalculatesCorrectValueForSpecifiedPercentage() {
        //given
        BigDecimal source = new BigDecimal("100.00");
        PercentageDecreaseFunction function = new PercentageDecreaseFunction(5);

        //when
        BigDecimal result = function.apply(source);

        //then
        assertThat(result).isEqualByComparingTo("95.00");
    }

    @Test
    void forZeroPercentageFunctionReturnsTheSameValue() {
        //given
        BigDecimal source = new BigDecimal("100.00");
        PercentageDecreaseFunction function = new PercentageDecreaseFunction(0);

        //when
        BigDecimal result = function.apply(source);

        //then
        assertThat(result).isEqualByComparingTo(source);
    }

    @Test
    void functionCalculatesCorrectValueForPercentageLowerThanOne() {
        //given
        BigDecimal source = new BigDecimal("100.00");
        PercentageDecreaseFunction function = new PercentageDecreaseFunction(0.1);

        //when
        BigDecimal result = function.apply(source);

        //then
        assertThat(result).isEqualByComparingTo("99.90");
    }

}