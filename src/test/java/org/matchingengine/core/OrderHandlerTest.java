package org.matchingengine.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.matchingengine.core.interfaces.Order;

import static org.junit.jupiter.api.Assertions.*;

class OrderHandlerTest {
    OrderHandler handler;

    Order buyOrder1;
    Order buyOrder2;
    Order sellOrder1;
    Order sellOrder2;

    @BeforeEach
    public void beforeEach() {
        handler = new OrderHandler();
        buyOrder1 = new BuyOrder("a", 1, 1);
        buyOrder2 = new BuyOrder("a", 2, 1);
        sellOrder1 = new SellOrder("b", 1, 3);
        sellOrder2 = new SellOrder("a", 1, 1);
    }

    @Test
    void apply() {
        Assertions.assertEquals(OrderStatus.NotFilled, handler.apply(buyOrder1));
        Assertions.assertEquals(OrderStatus.NotFilled, handler.apply(sellOrder1));
        Assertions.assertEquals(OrderStatus.Complete, handler.apply(sellOrder2));
        Assertions.assertEquals(OrderStatus.NotFilled, handler.apply(buyOrder2));
    }
}