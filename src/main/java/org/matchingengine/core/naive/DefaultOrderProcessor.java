package org.matchingengine.core.naive;

import org.matchingengine.core.naive.interfaces.Order;
import org.matchingengine.core.naive.interfaces.OrderProcessor;

import java.util.Iterator;

public class DefaultOrderProcessor implements OrderProcessor {
    @Override
    public int processOrder(Order order) {
        switch (order.getSide()) {
            case Side.BUY: return processBuy(order);
            case Side.SELL: return processSell(order);
            default: throw new IllegalArgumentException("No such side " + order.getSide());
        }
    }

    int processBuy(Order order) {
        int status = OrderStatus.NOT_FILLED;
        Iterator<Order> sellIterator = order.getOrderBook().getOrderIterator(Side.SELL);
        while (sellIterator.hasNext()) {
            Order sellOrder = sellIterator.next();
            if (status != OrderStatus.COMPLETE && sellOrder.getPrice() <= order.getPrice()) {
                status = process(sellOrder, order);
                if (sellOrder.getQuantity() == 0) {
                    sellIterator.remove();
                }
            } else {
                break;
            }
        }
        if (status != OrderStatus.COMPLETE) {
            order.getOrderBook().addOrder(order);
        }
        return status;
    }

    int processSell(Order order) {
        int status = OrderStatus.NOT_FILLED;
        Iterator<Order> buyIterator = order.getOrderBook().getOrderIterator(Side.BUY);
        while (buyIterator.hasNext()) {
            Order buyOrder = buyIterator.next();
            if (status != OrderStatus.COMPLETE && buyOrder.getPrice() >= order.getPrice()) {
                process(order, buyOrder);
                status = order.getQuantity() == 0 ? OrderStatus.COMPLETE : OrderStatus.PARTIAL;
                if (buyOrder.getQuantity() == 0) {
                    buyIterator.remove();
                }
            } else {
                break;
            }
        }
        if (status != OrderStatus.COMPLETE) {
            order.getOrderBook().addOrder(order);
        }
        return status;

    }

    int process(Order fromOrder, Order toOrder) {
        int status = OrderStatus.NOT_FILLED;
        if (fromOrder.getQuantity() > 0) {
            removeQuantity(fromOrder, toOrder);
            if (toOrder.getQuantity() > 0) {
                status = OrderStatus.PARTIAL;
            } else {
                status = OrderStatus.COMPLETE;
            }
        }
        return status;
    }

    void removeQuantity(Order fromOrder, Order toOrder) {
        int quantity = fromOrder.getQuantity();
        int orderQuantity = toOrder.getQuantity();
        if (quantity >= orderQuantity) {
            fromOrder.setQuantity(quantity - orderQuantity);
            toOrder.setQuantity(0);
        } else {
            toOrder.setQuantity(orderQuantity - quantity);
            fromOrder.setQuantity(0);
        }
    }
}
