package org.matchingengine.core;

import org.matchingengine.core.interfaces.Order;
import org.matchingengine.core.interfaces.OrderBook;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

public class OrderHandler implements Function<Order, OrderStatus> {

    private final ConcurrentMap<String, OrderBook> orderBooks = new ConcurrentHashMap<>();

    @Override
    public OrderStatus apply(Order order) {
        return orderBooks
                .computeIfAbsent(order.getInstrument(), OrderHandler::createOrderBook)
                .addOrder(order);
    }

    private static OrderBook createOrderBook(String instrument) {
        return new BuySellOrderBook(instrument, new DefaultOrderProcessor());
    }
}
