package org.matchingengine.core;

import org.matchingengine.core.interfaces.Order;
import org.matchingengine.core.interfaces.OrderBook;

public class BuyOrder extends BaseOrder {
    public BuyOrder(String instrument, int price, int quantity) {
        super(instrument, price, quantity);
    }
}
