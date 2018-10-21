package org.matchingengine.core.interfaces;

import org.matchingengine.core.OrderStatus;
import org.matchingengine.core.OrderType;

import java.util.Set;

public interface OrderBook {

    /**
     * Add the specified order to the order book.
     * @param order the order to add
     */
    OrderStatus addOrder(Order order);

    /**
     * Remove the specific order from the order book
     */
    void removeOrder(Order order);

    /**
     * Get the order iterator for the specified side of the order book
     * @param type the side of the order book
     * @return the order iterator
     */
    Set<Order> getOrders(OrderType type);
}
