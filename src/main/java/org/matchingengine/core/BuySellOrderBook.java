package org.matchingengine.core;

import org.matchingengine.core.interfaces.Order;
import org.matchingengine.core.interfaces.OrderBook;
import org.matchingengine.core.interfaces.OrderProcessor;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class BuySellOrderBook implements OrderBook {
    Set<Order> buyOrders = new ConcurrentSkipListSet<>(Comparator.comparing(Order::getPrice).reversed());
    Set<Order> sellOrders = new ConcurrentSkipListSet<>(Comparator.comparing(Order::getPrice));

    private final String instrument;
    private final OrderProcessor orderProcessor;

    public BuySellOrderBook(String instrument, OrderProcessor orderProcessor) {
        this.instrument = instrument;
        this.orderProcessor = orderProcessor;
    }

    @Override
    public synchronized OrderStatus addOrder(Order order) {
        if (order instanceof BuyOrder) {
            return addOrder((BuyOrder) order);
        } else if (order instanceof SellOrder) {
            return addOrder((SellOrder) order);
        } else {
            throw new IllegalArgumentException("Unsupported ordertype " + order.getClass().getName());
        }
    }

    OrderStatus addOrder(BuyOrder order) {
        OrderStatus status = OrderStatus.NotFilled;
        for (Order sellOrder : sellOrders) {
            if (status != OrderStatus.Complete && sellOrder.getPrice() <= order.getPrice()) {
                status = orderProcessor.apply(sellOrder, order);
                if (sellOrder.getQuantity() == 0) {
                    sellOrders.remove(sellOrder);
                }
            } else {
                break;
            }
        }
        if (status != OrderStatus.Complete) {
            buyOrders.add(order);
        }
        return status;
    }

    OrderStatus addOrder(SellOrder order) {
        OrderStatus status = OrderStatus.NotFilled;
        for (Order buyOrder : buyOrders) {
            if (status != OrderStatus.Complete && buyOrder.getPrice() >= order.getPrice()) {
                orderProcessor.apply(order, buyOrder);
                status = order.getQuantity() == 0 ? OrderStatus.Complete : OrderStatus.Partial;
                if (buyOrder.getQuantity() == 0) {
                    buyOrders.remove(buyOrder);
                }
            } else {
                break;
            }
        }
        if (status != OrderStatus.Complete) {
            sellOrders.add(order);
        }
        return status;
    }

    @Override
    public synchronized void removeOrder(Order order) {
        if (order instanceof BuyOrder) {
            buyOrders.remove(order);
        } else if (order instanceof SellOrder) {
            sellOrders.remove(order);
        } else {
            throw new IllegalArgumentException("Unsupported ordertype " + order.getClass().getName());
        }
    }

    @Override
    public Set<Order> getOrders(OrderType type) {
        switch (type) {
            case Buy:
                return Collections.unmodifiableSet(buyOrders);
            case Sell:
                return Collections.unmodifiableSet(sellOrders);
            default:
                return Collections.emptySet();
        }
    }
}
