package com.assignment.fxprices.convert;

import com.assignment.fxprices.model.InputPrice;

import java.util.List;

/**
 * Converter from the price feed string to a list of instances of specific model class representation.
 */
public interface InputPriceConverter {

    /**
     * Converts string that comes from the price feed to the list of {@link InputPrice} model class instances.
     *
     * @param value string value from the price feed
     * @return list of input prices
     */
    List<InputPrice> convert(String value);
}
