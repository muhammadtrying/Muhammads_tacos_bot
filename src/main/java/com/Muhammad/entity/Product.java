package com.Muhammad.entity;

import java.util.UUID;

public class Product {
    private final String name;
    private final Integer price;
    private final UUID id = UUID.randomUUID();

    public Product(String name, Integer price) {
        this.name = name;
        this.price = price;
    }
}
