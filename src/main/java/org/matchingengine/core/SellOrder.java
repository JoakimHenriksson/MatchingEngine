package org.matchingengine.core;

import org.matchingengine.core.interfaces.Order;
import org.matchingengine.core.interfaces.OrderBook;

public class SellOrder extends BaseOrder {

    public SellOrder(String instrument, int price, int quantity) {
        super(instrument, price, quantity);
    }

}
