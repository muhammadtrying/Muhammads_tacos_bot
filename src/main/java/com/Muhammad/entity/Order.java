package com.Muhammad.entity;


import com.Muhammad.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.UUID;


public class Order {
    private final UUID id = UUID.randomUUID();
    private Long userId;
    private OrderStatus orderStatus = OrderStatus.STARTED;
    private LocalDateTime localDateTime = LocalDateTime.now();


    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public UUID getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public static void showOrders() {

    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
