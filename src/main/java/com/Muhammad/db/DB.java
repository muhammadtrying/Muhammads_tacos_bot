package com.Muhammad.db;

import com.Muhammad.entity.Product;
import com.Muhammad.entity.TelegramUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public interface DB {
    ConcurrentHashMap<Long, TelegramUser> TELEGRAM_USERS = new ConcurrentHashMap<>();
    ArrayList<Product> PRODUCTS = new ArrayList<>(Arrays.asList(

     new Product("Jumper",100000),
     new Product("T-Shirt",80000),
     new Product("Trousers",90000),
     new Product("Air_Jordans",300000),
     new Product("Banana",20000),
     new Product("Apple",30000),
     new Product("Meat",100000),
     new Product("Watermelon",15000),
     new Product("Cake",60000),
     new Product("Pepsi",15000),
     new Product("Water",7000),
     new Product("Sprite",12000),
     new Product("COKE",12000),
     new Product("CHOCOLATE",30000),
     new Product("ICE_CREAM",10000),
     new Product("HONEY",75000)
    ));
}
