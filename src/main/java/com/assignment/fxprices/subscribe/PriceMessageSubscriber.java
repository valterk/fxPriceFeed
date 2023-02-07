package com.assignment.fxprices.subscribe;

/**
 * An interface that has to be implemented by listeners of the price feed.
 */
public interface PriceMessageSubscriber {

    /**
     * An action performed when a message from the price feed arrived.
     *
     * @param message message from the price feed
     */
    void onMessage(String message);
}
