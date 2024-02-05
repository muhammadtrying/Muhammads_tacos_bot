package com.Muhammad.db;

import com.Muhammad.entity.Order;
import com.Muhammad.entity.OrderProduct;
import com.Muhammad.entity.Product;
import com.Muhammad.entity.TelegramUser;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface DB {
    List<Order> ORDERS = new ArrayList<>();
    String PATH_OF_USERS = "src/main/java/com/Muhammad/db/databaseUsers.txt";
    List<OrderProduct> ORDER_PRODUCT = new ArrayList<>();
    ConcurrentHashMap<Long, TelegramUser> TELEGRAM_USERS = new ConcurrentHashMap<>();
    ArrayList<Product> PRODUCTS = new ArrayList<>(Arrays.asList(

     new Product("Jumper", 100000), new Product("T-Shirt", 80000), new Product("Trousers", 90000), new Product("Air Jordans(En)", 300000), new Product("Banana", 20000), new Product("Apples", 30000), new Product("Meat", 100000), new Product("Watermelon", 15000), new Product("Cake", 60000), new Product("Pepsi", 15000), new Product("Water", 7000), new Product("Sprite", 12000), new Product("Coke", 12000), new Product("Chocolate", 30000), new Product("ICE Cream", 10000), new Product("Honey", 75000)));
    List<Integer> deletedMessages = new ArrayList<>();
    String PATH_OF_ORDERS = "src/main/java/com/Muhammad/db/databaseOrders.txt";

    static void uploadUsers(Map<Long, TelegramUser> users) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(PATH_OF_USERS); ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(users);
        } catch (IOException e) {

        }
    }

    static void uploadOrders(List<Order> orders) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(PATH_OF_ORDERS); ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(orders);
        } catch (IOException e) {

        }
    }

    @SuppressWarnings("unchecked")
    static ConcurrentHashMap<Long, TelegramUser> loadUsers() {
        try (FileInputStream fileInputStream = new FileInputStream(PATH_OF_USERS);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            return (ConcurrentHashMap<Long, TelegramUser>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ConcurrentHashMap<>();
        }
    }

    @SuppressWarnings("unchecked")
    static List<Order> loadOrders() {
        try (FileInputStream fileInputStream = new FileInputStream(PATH_OF_ORDERS);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            return (List<Order>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}
