package com.Muhammad.entity;


import java.util.UUID;


public class OrderProduct {
    private final UUID orderId;
    private final UUID productId;
    private final int amount;

    public OrderProduct(UUID orderId, UUID productId, int amount) {
        this.orderId = orderId;
        this.productId = productId;
        this.amount = amount;
    }


    public UUID getOrderId() {
        return orderId;
    }


    public UUID getProductId() {
        return productId;
    }


    public int getAmount() {
        return amount;
    }

}
