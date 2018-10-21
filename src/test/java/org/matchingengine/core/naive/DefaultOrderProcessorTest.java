package org.matchingengine.core.naive;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.matchingengine.core.naive.interfaces.Order;
import org.matchingengine.core.naive.interfaces.OrderBook;
import org.matchingengine.core.naive.interfaces.OrderProcessor;

import static org.junit.jupiter.api.Assertions.*;

class DefaultOrderProcessorTest {

    Order buyOrder1;
    Order buyOrder2;
    Order buyOrder3;
    Order buyOrder4;
    Order sellOrder1;
    Order sellOrder2;
    Order sellOrder3;

    OrderBook orderBook;
    OrderProcessor orderProcessor;
    @BeforeEach
    void setUp() {
        orderProcessor = new DefaultOrderProcessor();
        orderBook = new DefaultOrderBook(orderProcessor);
        buyOrder1 = new DefaultOrder(orderBook, Side.BUY, 1, 1);
        buyOrder2 = new DefaultOrder(orderBook, Side.BUY, 2, 1);
        buyOrder3 = new DefaultOrder(orderBook, Side.BUY, 3, 1);
        buyOrder4 = new DefaultOrder(orderBook, Side.BUY, 1, 5);
        sellOrder1 = new DefaultOrder(orderBook, Side.SELL, 1, 3);
        sellOrder2 = new DefaultOrder(orderBook, Side.SELL, 3, 1);
        sellOrder3 = new DefaultOrder(orderBook, Side.SELL, 1, 1);
    }

    @Test
    void processOrder1() {
        Assertions.assertEquals(OrderStatus.NOT_FILLED, orderProcessor.processOrder(sellOrder1));
        Assertions.assertEquals(OrderStatus.COMPLETE, orderProcessor.processOrder(buyOrder1));
        Assertions.assertEquals(OrderStatus.COMPLETE, orderProcessor.processOrder(buyOrder2));
        Assertions.assertEquals(OrderStatus.COMPLETE, orderProcessor.processOrder(buyOrder3));
        Assertions.assertFalse(orderBook.getOrderIterator(Side.BUY).hasNext());
        Assertions.assertFalse(orderBook.getOrderIterator(Side.SELL).hasNext());
    }

    @Test
    void processOrder2() {
        orderProcessor.processOrder(sellOrder2);
        Assertions.assertEquals(OrderStatus.NOT_FILLED, orderProcessor.processOrder(buyOrder1));
        Assertions.assertEquals(OrderStatus.NOT_FILLED, orderProcessor.processOrder(buyOrder2));
        Assertions.assertEquals(OrderStatus.COMPLETE, orderProcessor.processOrder(buyOrder3));
        Assertions.assertFalse(orderBook.getOrderIterator(Side.SELL).hasNext());
        Assertions.assertTrue(orderBook.getOrderIterator(Side.BUY).hasNext());
    }

    @Test
    void processOrder3() {
        orderProcessor.processOrder(buyOrder1);
        Assertions.assertEquals(OrderStatus.PARTIAL, orderProcessor.processOrder(sellOrder1));
        Assertions.assertEquals(OrderStatus.PARTIAL, orderProcessor.processOrder(buyOrder4));
    }
}