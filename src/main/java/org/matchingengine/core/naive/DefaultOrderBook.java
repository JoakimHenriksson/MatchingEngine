package org.matchingengine.core.naive;

import org.matchingengine.core.DefaultOrderProcessor;
import org.matchingengine.core.naive.interfaces.Order;
import org.matchingengine.core.naive.interfaces.OrderBook;
import org.matchingengine.core.naive.interfaces.OrderProcessor;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class DefaultOrderBook implements OrderBook {
    private OrderProcessor orderProcessor;

    private final Set<Order> buyOrders = new ConcurrentSkipListSet<>(Comparator.comparing(Order::getPrice).reversed());
    private final Set<Order> sellOrders = new ConcurrentSkipListSet<>(Comparator.comparing(Order::getPrice));

    public DefaultOrderBook() {}

    public DefaultOrderBook(OrderProcessor orderProcessor) {
        this.orderProcessor = orderProcessor;
    }

    @Override
    public void addOrder(Order order) {
        switch (order.getSide()) {
            case Side.BUY:
                buyOrders.add(order);
                break;
            case Side.SELL:
                sellOrders.add(order);
                break;
            default:
                throw new IllegalArgumentException("No such side " + order.getSide());
        }
    }

    @Override
    public Iterator<Order> getOrderIterator(int side) {
        switch (side) {
            case Side.BUY: return buyOrders.iterator();
            case Side.SELL: return sellOrders.iterator();
            default: throw new IllegalArgumentException("No such side " + side);
        }
    }

    @Override
    public OrderProcessor getOrderProcessor() {
        return orderProcessor;
    }

    @Override
    public void setOrderProcessor(OrderProcessor orderProcessor) {
        this.orderProcessor = orderProcessor;
    }
}
