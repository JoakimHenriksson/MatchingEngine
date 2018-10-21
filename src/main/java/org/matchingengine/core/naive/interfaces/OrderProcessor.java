package org.matchingengine.core.naive.interfaces;

/**
 * OrderProcessor.processOrder is redundant.
 * Both the interface name AND the argument type says it is
 * an order.
 */
public interface OrderProcessor {

    /**
     * Process the specified order and return the order status
     * @param order the order to process
     * @return the order status code
     */
    int processOrder(Order order);
}
