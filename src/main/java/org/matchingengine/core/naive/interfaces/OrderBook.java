package org.matchingengine.core.naive.interfaces;

import java.util.Iterator;

/**
 * poorly designed interface.
 * Breaks stacking rule.
 * Hard to test
 * Is inherently racy.
 * Nonfinal dependencies is problematic.
 * Directly exposing functionality of internal datastructures is a very bad idea.
 */
public interface OrderBook {

    /**
     * Add the specified order to the order book.
     * @param order the order to add
     */
    void addOrder(Order order);

    /**
     * Get the order iterator for the specified side of the order book
     * @param side the side of the order book
     * @return the order iterator
     */
    Iterator<Order> getOrderIterator(int side);

    /**
     * Get the order processor for this order book
     * @return the order processor
     */
    OrderProcessor getOrderProcessor();

    /**
     * Set the order processor for this order book
     * @param orderProcessor the order processor to set
     */
    void setOrderProcessor(OrderProcessor orderProcessor);
}
