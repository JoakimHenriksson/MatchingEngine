package org.matchingengine.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.matchingengine.core.interfaces.Order;
import org.matchingengine.core.interfaces.OrderBook;
import org.matchingengine.core.interfaces.OrderProcessor;

class BuySellOrderBookTest {
    OrderBook orderBook;
    Order buyOrder1;
    Order buyOrder2;
    Order buyOrder3;
    Order sellOrder1;
    Order sellOrder2;
    Order sellOrder3;
    Order unsupportedOrder;

    private static class UnsupportedOrder extends BaseOrder {
        public UnsupportedOrder(String instrument, int price, int quantity) {
            super(instrument, price, quantity);
        }
    }

    @BeforeEach
    public void beforeEach() {
        OrderProcessor orderProcessor = new DefaultOrderProcessor();
        orderBook = new BuySellOrderBook("a", orderProcessor);
        buyOrder1 = new BuyOrder("a", 1, 1);
        buyOrder2 = new BuyOrder("a", 2, 1);
        buyOrder3 = new BuyOrder("a", 3, 1);
        sellOrder1 = new SellOrder("a", 1, 3);
        sellOrder2 = new SellOrder("a", 1, 1);
        sellOrder3 = new SellOrder("a", 3, 1);
        unsupportedOrder = new UnsupportedOrder("a", 1, 1);
    }

    @Test
    void addOrder() {
        Assertions.assertEquals(OrderStatus.NotFilled, orderBook.addOrder(sellOrder1));
        Assertions.assertEquals(OrderStatus.Complete, orderBook.addOrder(buyOrder2));
        Assertions.assertEquals(OrderStatus.Complete, orderBook.addOrder(buyOrder3));
        Assertions.assertEquals(OrderStatus.Complete, orderBook.addOrder(buyOrder1));
        Assertions.assertTrue(orderBook.getOrders(OrderType.Buy).isEmpty());
        Assertions.assertTrue(orderBook.getOrders(OrderType.Sell).isEmpty());
        Assertions.assertThrows(IllegalArgumentException.class, () -> orderBook.addOrder(unsupportedOrder));
    }

    @Test
    void addBuyOrder() {
        orderBook.addOrder(sellOrder2);
        orderBook.addOrder(sellOrder3);
        Assertions.assertEquals(OrderStatus.Complete, orderBook.addOrder((BuyOrder) buyOrder3));
        Assertions.assertTrue(orderBook.getOrders(OrderType.Sell).contains(sellOrder3));
        Assertions.assertFalse(orderBook.getOrders(OrderType.Sell).contains(sellOrder2));
    }

    @Test
    void addSellOrder() {
        orderBook.addOrder(buyOrder2);
        orderBook.addOrder(buyOrder3);
        Assertions.assertEquals(OrderStatus.Complete, orderBook.addOrder(sellOrder2));
        Assertions.assertTrue(orderBook.getOrders(OrderType.Buy).contains(buyOrder2));
        Assertions.assertFalse(orderBook.getOrders(OrderType.Buy).contains(buyOrder3));
    }

    @Test
    void removeBuyOrder() {
        orderBook.addOrder(buyOrder3);
        orderBook.removeOrder(buyOrder2);
        Assertions.assertTrue(orderBook.getOrders(OrderType.Buy).contains(buyOrder3));
        orderBook.removeOrder(buyOrder3);
        Assertions.assertFalse(orderBook.getOrders(OrderType.Buy).contains(buyOrder3));
        Assertions.assertTrue(orderBook.getOrders(OrderType.Buy).isEmpty());
    }

    @Test
    void removeSellOrder() {
        orderBook.addOrder(sellOrder3);
        orderBook.removeOrder(sellOrder2);
        Assertions.assertTrue(orderBook.getOrders(OrderType.Sell).contains(sellOrder3));
        orderBook.removeOrder(sellOrder3);
        Assertions.assertFalse(orderBook.getOrders(OrderType.Sell).contains(sellOrder3));
        Assertions.assertTrue(orderBook.getOrders(OrderType.Sell).isEmpty());
    }
}