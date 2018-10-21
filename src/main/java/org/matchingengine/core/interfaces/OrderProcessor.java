package org.matchingengine.core.interfaces;

import org.matchingengine.core.OrderStatus;

import java.util.List;
import java.util.function.BiFunction;

public interface OrderProcessor extends BiFunction<Order, Order, OrderStatus> {
}
