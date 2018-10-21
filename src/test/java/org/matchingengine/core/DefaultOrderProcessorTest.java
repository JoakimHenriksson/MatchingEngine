package org.matchingengine.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.matchingengine.core.interfaces.Order;
import org.matchingengine.core.interfaces.OrderProcessor;

import static org.junit.jupiter.api.Assertions.*;

class DefaultOrderProcessorTest {
    OrderProcessor orderProcessor = new DefaultOrderProcessor();

    Order buyOrder1;
    Order buyOrder2;
    Order buyOrder3;
    Order sellOrder1;
    Order sellOrder2;
    Order sellOrder3;

    @BeforeEach
    void setUp() {
        buyOrder1 = new BuyOrder("a", 1, 1);
        buyOrder2 = new BuyOrder("a", 2, 0);
        buyOrder3 = new BuyOrder("a", 3, 2);
        sellOrder1 = new SellOrder("a", 1, 3);
        sellOrder2 = new SellOrder("a", 1, 0);
        sellOrder3 = new SellOrder("a", 3, 1);
    }

    @Test
    void processComplete() {
        Assertions.assertEquals(OrderStatus.Complete, orderProcessor.process(sellOrder1, buyOrder1));
        Assertions.assertEquals(OrderStatus.Complete, orderProcessor.process(sellOrder1, buyOrder2));
    }

    @Test
    void processNotFilled() {
        Assertions.assertEquals(OrderStatus.NotFilled, orderProcessor.process(sellOrder2, buyOrder1));
    }

    @Test
    void processPartial() {
        Assertions.assertEquals(OrderStatus.Partial, orderProcessor.process(sellOrder3, buyOrder3));
    }
}