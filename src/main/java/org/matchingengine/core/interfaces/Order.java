package org.matchingengine.core.interfaces;

public interface Order {
    default void removeQuantity(Order order) {
        int quantity = getQuantity();
        int orderQuantity = order.getQuantity();
        if (quantity >= orderQuantity) {
            setQuantity(quantity - orderQuantity);
            order.setQuantity(0);
        } else {
            order.setQuantity(orderQuantity - quantity);
            setQuantity(0);
        }
    }

    String getInstrument();

    /**
     * The price for this order
     * @return the price
     */
    int getPrice();

    /**
     * The quantity for this order
     * @return the quantity
     */
    int getQuantity();

    /**
     * Set the quantity for this order
     * @param quantity the quantity
     */
    void setQuantity(int quantity);
}
