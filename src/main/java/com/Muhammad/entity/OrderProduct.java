package com.Muhammad.entity;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;


public class OrderProduct {
    private final UUID id = UUID.randomUUID();
    private UUID orderId;
    private UUID productId;
    private int amount;

    public OrderProduct(UUID productId) {
        this.productId = productId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
