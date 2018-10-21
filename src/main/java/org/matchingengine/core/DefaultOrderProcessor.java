package org.matchingengine.core;

import org.matchingengine.core.interfaces.Order;
import org.matchingengine.core.interfaces.OrderProcessor;

public class DefaultOrderProcessor implements OrderProcessor {
    @Override
    public OrderStatus apply(Order fromOrder, Order toOrder) {
        OrderStatus status = OrderStatus.NotFilled;
        if (fromOrder.getQuantity() > 0) {
            fromOrder.removeQuantity(toOrder);
            if (toOrder.getQuantity() > 0) {
                status = OrderStatus.Partial;
            } else {
                status = OrderStatus.Complete;
            }
        }
        return status;
    }
}
