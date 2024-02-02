package com.Muhammad.entity;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;
@Data
@Builder
public class OrderProduct {
    private UUID id;
    private UUID orderId;
    private UUID productId;

}
