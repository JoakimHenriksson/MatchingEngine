package org.matchingengine.core.naive;

import org.matchingengine.core.naive.interfaces.Order;
import org.matchingengine.core.naive.interfaces.OrderBook;

public class DefaultOrder implements Order {
    private OrderBook orderBook;
    private int side;
    private int price;
    private int quantity;

    public DefaultOrder() {
    }

    public DefaultOrder(OrderBook orderBook, int side, int price, int quantity) {
        this.orderBook = orderBook;
        this.side = side;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public OrderBook getOrderBook() {
        return orderBook;
    }

    @Override
    public void setOrderBook(OrderBook orderBook) {
        this.orderBook = orderBook;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int getSide() {
        return side;
    }

    @Override
    public void setSide(int side) {
        this.side = side;
    }
}
