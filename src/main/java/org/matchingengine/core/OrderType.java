package org.matchingengine.core;

public enum OrderType {
    Buy(1),
    Sell(2),
    Unsupported(Integer.MIN_VALUE);

    private final int value;

    private OrderType(int value) {
        this.value = value;
    }
}
