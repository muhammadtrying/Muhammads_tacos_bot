package com.Muhammad.entity;


import com.Muhammad.enums.OrderStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;


public class Order implements Serializable {
    private final UUID id = UUID.randomUUID();
    private Long userId;
    private OrderStatus orderStatus = OrderStatus.STARTED;
    private final LocalDateTime localDateTime = LocalDateTime.now();


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

    public static void showOrders() {
       //
    }
}
