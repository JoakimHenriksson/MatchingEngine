package org.matchingengine.core;

public enum OrderStatus {
    Complete(2),
    NotFilled(4),
    Partial(6);

    private final int value;
    private OrderStatus(int value) {
        this.value = value;
    }
}
