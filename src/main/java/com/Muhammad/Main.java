package com.Muhammad;

import com.Muhammad.bot.MyBot;
import com.Muhammad.entity.Order;
import com.Muhammad.entity.TelegramUser;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // starting the bot
        MyBot myBot = new MyBot();
        myBot.start();

        //  an admin panel to see what the heck is going on
        while (true) {
            System.out.println("""
             1. Show Users
             2. Show Orders
             """);
            Scanner scanner = new Scanner(System.in);
            switch (scanner.nextInt()) {
                case 1 -> TelegramUser.showUsers();
                case 2 -> Order.showOrders();
            }
        }
    }
}