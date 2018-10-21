package org.matchingengine.core;

import org.matchingengine.core.interfaces.Order;

public abstract class BaseOrder implements Order {
    protected final String instrument;
    protected final int price;
    protected int quantity;

    public BaseOrder(String instrument, int price, int quantity) {
        this.instrument = instrument;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String getInstrument() {
        return instrument;
    }

    @Override
    public int getPrice() {
        return price;
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
    public String toString() {
        return String.format("instrument=%s price=%d, quantity=%d, type=%s", instrument, price, quantity, getClass().getSimpleName());
    }
}
