package com.Muhammad.bot;

import com.Muhammad.db.DB;
import com.Muhammad.entity.Order;
import com.Muhammad.entity.OrderProduct;
import com.Muhammad.entity.Product;
import com.Muhammad.entity.TelegramUser;
import com.Muhammad.enums.Language;
import com.Muhammad.enums.TelegramState;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Contact;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.DeleteMessages;
import com.pengrad.telegrambot.request.EditMessageReplyMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.SendResponse;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BotService {
    public static void acceptStartAskLanguage(Message message, TelegramUser telegramUser) {
        SendMessage sendMessage = new SendMessage(message.chat().id(), """
         Assalomu aleykum. Botga hush kelibsiz. Iltimos tilni tanlang!
                   
         Hi, welcome to the bot. Please select language to continue!
         """);

        sendMessage.replyMarkup(BotUtils.generateLangBtns());
        SendResponse response = MyBot.telegramBot.execute(sendMessage);
        DB.deletedMessages.add(response.message().messageId());
        telegramUser.setTelegramState(TelegramState.SELECT_LANG);
    }

    public static void acceptLanguageAskContact(TelegramUser telegramUser, CallbackQuery callbackQuery) {
        String data = callbackQuery.data();

        //setting the chosen language to the user & setting full-name, too.
        telegramUser.setLanguage(Language.valueOf(data));
        telegramUser.setFirstName(callbackQuery.from().firstName());
        telegramUser.setLastName(callbackQuery.from().lastName());

        SendMessage sendMessage = new SendMessage(callbackQuery.from().id(), telegramUser.getText("ASK_CONTACT").formatted(getFullName(telegramUser)));


        sendMessage.replyMarkup(BotUtils.generateContactBtns(telegramUser));
        SendResponse response = MyBot.telegramBot.execute(sendMessage);
        DB.deletedMessages.add(response.message().messageId());

        //changing the state
        telegramUser.setTelegramState(TelegramState.SHARE_CONTACT);
    }

    private static String getFullName(TelegramUser telegramUser) {
        String name = "";
        if ( telegramUser.getFirstName() != null ) {
            name += telegramUser.getFirstName();
        }
        if ( telegramUser.getLastName() != null ) {
            name += telegramUser.getLastName();
        }
        return name;
    }

    public static void acceptContactShowCategories(TelegramUser telegramUser, Contact contact) {
        clearMessages(telegramUser);
        if ( contact != null ) {
            telegramUser.setPhoneNumber(contact.phoneNumber());
        }
        SendMessage sendMessage = new SendMessage(telegramUser.getChatId(), telegramUser.getText("CHOOSE_CATEGORY"));
        sendMessage.replyMarkup(BotUtils.generateCategoryButton(telegramUser));
        SendResponse response = MyBot.telegramBot.execute(sendMessage);
        DB.deletedMessages.add(response.message().messageId());
        telegramUser.setTelegramState(TelegramState.SHOW_CATEGORIES);
    }

    public static void showCategoryClothes(TelegramUser telegramUser) {
        SendMessage sendMessage = new SendMessage(telegramUser.getChatId(), telegramUser.getText("CLOTHES"));

        // to remove previous buttons
        sendMessage.replyMarkup(new ReplyKeyboardRemove());

        sendMessage.replyMarkup(BotUtils.generateClothesBtns(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendMessage);
        DB.deletedMessages.add(execute.message().messageId());
    }

    public static void showCategoryConsumables(TelegramUser telegramUser) {
        SendMessage sendMessage = new SendMessage(telegramUser.getChatId(), telegramUser.getText("CONSUMABLES"));

        // to remove previous buttons
        sendMessage.replyMarkup(new ReplyKeyboardRemove());

        sendMessage.replyMarkup(BotUtils.generateConsumablesBtns(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendMessage);
        DB.deletedMessages.add(execute.message().messageId());
    }

    public static void showCategoryDrinks(TelegramUser telegramUser) {
        SendMessage sendMessage = new SendMessage(telegramUser.getChatId(), telegramUser.getText("DRINKS"));

        sendMessage.replyMarkup(new ReplyKeyboardRemove());
        sendMessage.replyMarkup(BotUtils.generateDrinksBtns(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendMessage);
        DB.deletedMessages.add(execute.message().messageId());
    }

    public static void showCategorySnacks(TelegramUser telegramUser) {
        SendMessage sendMessage = new SendMessage(telegramUser.getChatId(), telegramUser.getText("SNACKS"));

        sendMessage.replyMarkup(new ReplyKeyboardRemove());
        sendMessage.replyMarkup(BotUtils.generateSnacksBtns(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendMessage);
        DB.deletedMessages.add(execute.message().messageId());
    }

    public static void showProductJumper(TelegramUser telegramUser) {
        SendPhoto sendPhoto = new SendPhoto(telegramUser.getChatId(), new File("src/main/java/com/Muhammad/photos/jumper.png"));

        sendPhoto.replyMarkup(new ReplyKeyboardRemove());
        sendPhoto.caption(telegramUser.getText("JUMPER") + " 100,000 sum");
        sendPhoto.replyMarkup(new ReplyKeyboardRemove());
        sendPhoto.replyMarkup(BotUtils.generateCounter(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendPhoto);
        DB.deletedMessages.add(execute.message().messageId());
        telegramUser.setMessageId(execute.message().messageId());
    }

    public static void showProductTshirt(TelegramUser telegramUser) {
        SendPhoto sendPhoto = new SendPhoto(telegramUser.getChatId(), new File("src/main/java/com/Muhammad/photos/t-shirt.png"));
        sendPhoto.caption(telegramUser.getText("T-SHIRT") + " 80,000  sum");
        sendPhoto.replyMarkup(BotUtils.generateCounter(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendPhoto);
        DB.deletedMessages.add(execute.message().messageId());
        telegramUser.setMessageId(execute.message().messageId());
    }

    public static void showProductTrousers(TelegramUser telegramUser) {
        SendPhoto sendPhoto = new SendPhoto(telegramUser.getChatId(), new File("src/main/java/com/Muhammad/photos/trousers.png"));
        sendPhoto.caption(telegramUser.getText("TROUSERS") + " 90,000 sum");

        sendPhoto.replyMarkup(BotUtils.generateCounter(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendPhoto);
        DB.deletedMessages.add(execute.message().messageId());
        telegramUser.setMessageId(execute.message().messageId());
    }

    public static void showProductAirJordans(TelegramUser telegramUser) {
        SendPhoto sendPhoto = new SendPhoto(telegramUser.getChatId(), new File("src/main/java/com/Muhammad/photos/air jordans.png"));
        sendPhoto.caption(telegramUser.getText("AIR_JORDANS") + " 300,000 sum");
        sendPhoto.replyMarkup(BotUtils.generateCounter(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendPhoto);
        DB.deletedMessages.add(execute.message().messageId());
        telegramUser.setMessageId(execute.message().messageId());
    }

    public static void showProductBanana(TelegramUser telegramUser) {
        SendPhoto sendPhoto = new SendPhoto(telegramUser.getChatId(), new File("src/main/java/com/Muhammad/photos/Screenshot 2024-02-02 at 7.44.51 PM.png"));
        sendPhoto.caption(telegramUser.getText("BANANA") + " 20,000 sum");
        sendPhoto.replyMarkup(BotUtils.generateCounter(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendPhoto);
        DB.deletedMessages.add(execute.message().messageId());
        telegramUser.setMessageId(execute.message().messageId());
    }

    public static void showProductApples(TelegramUser telegramUser) {
        SendPhoto sendPhoto = new SendPhoto(telegramUser.getChatId(), new File("src/main/java/com/Muhammad/photos/Screenshot 2024-02-02 at 7.44.24 PM.png"));
        sendPhoto.caption(telegramUser.getText("APPLES") + " 30,000 sum");
        sendPhoto.replyMarkup(BotUtils.generateCounter(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendPhoto);
        DB.deletedMessages.add(execute.message().messageId());
        telegramUser.setMessageId(execute.message().messageId());
    }

    public static void showProductMeat(TelegramUser telegramUser) {
        SendPhoto sendPhoto = new SendPhoto(telegramUser.getChatId(), new File("src/main/java/com/Muhammad/photos/Screenshot 2024-02-02 at 7.47.53 PM.png"));
        sendPhoto.caption(telegramUser.getText("MEAT") + " 100,000 sum");
        sendPhoto.replyMarkup(BotUtils.generateCounter(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendPhoto);
        DB.deletedMessages.add(execute.message().messageId());
        telegramUser.setMessageId(execute.message().messageId());
    }

    public static void showProductWatermelon(TelegramUser telegramUser) {
        SendPhoto sendPhoto = new SendPhoto(telegramUser.getChatId(), new File("src/main/java/com/Muhammad/photos/watermelon.png"));
        sendPhoto.caption(telegramUser.getText("WATERMELON") + " 15,000 sum");
        sendPhoto.replyMarkup(BotUtils.generateCounter(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendPhoto);
        DB.deletedMessages.add(execute.message().messageId());
        telegramUser.setMessageId(execute.message().messageId());
    }

    public static void showProductCake(TelegramUser telegramUser) {
        SendPhoto sendPhoto = new SendPhoto(telegramUser.getChatId(), new File("src/main/java/com/Muhammad/photos/Screenshot 2024-02-02 at 7.45.14 PM.png"));
        sendPhoto.caption(telegramUser.getText("CAKE") + " 60,000 sum");
        sendPhoto.replyMarkup(BotUtils.generateCounter(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendPhoto);
        DB.deletedMessages.add(execute.message().messageId());
        telegramUser.setMessageId(execute.message().messageId());
    }

    public static void showProductPepsi(TelegramUser telegramUser) {
        SendPhoto sendPhoto = new SendPhoto(telegramUser.getChatId(), new File("src/main/java/com/Muhammad/photos/pepsi.png"));
        sendPhoto.caption(telegramUser.getText("PEPSI") + " 15,000 sum");
        sendPhoto.replyMarkup(BotUtils.generateCounter(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendPhoto);
        DB.deletedMessages.add(execute.message().messageId());
        telegramUser.setMessageId(execute.message().messageId());
    }

    public static void showProductWater(TelegramUser telegramUser) {
        SendPhoto sendPhoto = new SendPhoto(telegramUser.getChatId(), new File("src/main/java/com/Muhammad/photos/water.png"));
        sendPhoto.caption(telegramUser.getText("WATER") + " 7,000 sum");
        sendPhoto.replyMarkup(BotUtils.generateCounter(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendPhoto);
        DB.deletedMessages.add(execute.message().messageId());
        telegramUser.setMessageId(execute.message().messageId());
    }

    public static void showProductSprite(TelegramUser telegramUser) {
        SendPhoto sendPhoto = new SendPhoto(telegramUser.getChatId(), new File("src/main/java/com/Muhammad/photos/sprite.png"));
        sendPhoto.caption(telegramUser.getText("SPRITE") + " 12,000 sum");
        sendPhoto.replyMarkup(BotUtils.generateCounter(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendPhoto);
        DB.deletedMessages.add(execute.message().messageId());
        telegramUser.setMessageId(execute.message().messageId());
    }

    public static void showProductCoke(TelegramUser telegramUser) {
        SendPhoto sendPhoto = new SendPhoto(telegramUser.getChatId(), new File("src/main/java/com/Muhammad/photos/coke.png"));
        sendPhoto.caption(telegramUser.getText("COKE") + " 12,000 sum");
        sendPhoto.replyMarkup(BotUtils.generateCounter(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendPhoto);
        DB.deletedMessages.add(execute.message().messageId());
        telegramUser.setMessageId(execute.message().messageId());
    }

    public static void showProductChocolate(TelegramUser telegramUser) {
        SendPhoto sendPhoto = new SendPhoto(telegramUser.getChatId(), new File("src/main/java/com/Muhammad/photos/chocolate.png"));
        sendPhoto.caption(telegramUser.getText("CHOCOLATE") + " 30,000 sum");
        sendPhoto.replyMarkup(BotUtils.generateCounter(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendPhoto);
        DB.deletedMessages.add(execute.message().messageId());
        telegramUser.setMessageId(execute.message().messageId());
    }

    public static void showProductIceCream(TelegramUser telegramUser) {
        SendPhoto sendPhoto = new SendPhoto(telegramUser.getChatId(), new File("src/main/java/com/Muhammad/photos/ice cream.png"));
        sendPhoto.caption(telegramUser.getText("ICE_CREAM") + " 10,000 sum");
        sendPhoto.replyMarkup(BotUtils.generateCounter(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendPhoto);
        DB.deletedMessages.add(execute.message().messageId());
        telegramUser.setMessageId(execute.message().messageId());
    }

    public static void showProductHoney(TelegramUser telegramUser) {
        SendPhoto sendPhoto = new SendPhoto(telegramUser.getChatId(), new File("src/main/java/com/Muhammad/photos/honey.png"));
        sendPhoto.caption(telegramUser.getText("HONEY") + " 70,000 sum");
        sendPhoto.replyMarkup(BotUtils.generateCounter(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendPhoto);
        DB.deletedMessages.add(execute.message().messageId());
        telegramUser.setMessageId(execute.message().messageId());
    }

    public static void dealWithEachProduct(TelegramUser telegramUser, CallbackQuery callbackQuery) {
        try {
            String data = callbackQuery.data();

            if ( data.equals("plus") ) {
                telegramUser.setCounter(telegramUser.getCounter() + 1);
            } else if ( data.equals("minus") && telegramUser.getCounter() > 0 ) {
                telegramUser.setCounter(telegramUser.getCounter() - 1);
            } else if ( data.equals("basket") ) {
                if ( telegramUser.getCounter() != 0 ) {
                    if ( telegramUser.basket != null ) {
                        int currentQuantity = telegramUser.basket.getOrDefault(telegramUser.getChosenProduct(), 0);
                        telegramUser.basket.put(telegramUser.getChosenProduct(), currentQuantity + telegramUser.getCounter());
                        telegramUser.setCounter(0);
                    } else {
                        telegramUser.basket = new HashMap<>();
                        telegramUser.basket.put(telegramUser.getChosenProduct(), telegramUser.getCounter());
                        telegramUser.setCounter(0);
                    }
                    return;
                }

            } else if ( data.equals("back") ) {
                telegramUser.setCounter(0);
                clearMessages(telegramUser);
                BotService.acceptContactShowCategories(telegramUser, null);
                return;
            }

            EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup(telegramUser.getChatId(), telegramUser.getMessageId());

            editMessageReplyMarkup.replyMarkup((InlineKeyboardMarkup) BotUtils.generateCounter(telegramUser));
            MyBot.telegramBot.execute(editMessageReplyMarkup);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clearMessages(TelegramUser telegramUser) {
        List<Integer> deletedMessagesList = DB.deletedMessages;
        int[] arr = deletedMessagesList.stream().mapToInt(Integer::intValue).toArray();

        DeleteMessages deleteMessages = new DeleteMessages(telegramUser.getChatId(), arr);
        MyBot.telegramBot.execute(deleteMessages);
    }

    public static void showBasket(TelegramUser telegramUser) {
        if ( telegramUser.basket != null ) {
            StringBuilder message = new StringBuilder();
            for (Map.Entry<Product, Integer> entry : telegramUser.basket.entrySet()) {
                Product product = entry.getKey();
                Integer amount = entry.getValue();
                message.append(product.getName()).append(" x ").append(amount).append("  [").append(product.getPrice() * amount).append(" sum]\n\n");
            }
            SendMessage sendMessage = new SendMessage(telegramUser.getChatId(), message.toString());
            sendMessage.replyMarkup(BotUtils.generateCancelButton(telegramUser));
            SendResponse execute = MyBot.telegramBot.execute(sendMessage);
            DB.deletedMessages.add(execute.message().messageId());
        }
    }


    public static void order(TelegramUser telegramUser) {
        Order order = new Order();
        for (Map.Entry<Product, Integer> entry : telegramUser.basket.entrySet()) {
            Integer amount = entry.getValue();
            Product product = entry.getKey();

            OrderProduct orderProduct = new OrderProduct(
             order.getId(),
             product.getId(),
             amount);

            DB.ORDER_PRODUCT.add(orderProduct);
        }

        order.setUserId(telegramUser.getChatId());

        DB.ORDERS.add(order);
        DB.uploadOrders(DB.ORDERS);
        telegramUser.basket.clear();
    }

    public static void showMyOrders(TelegramUser telegramUser) {
        int i = 1;
        int sum = 0;
        StringBuilder str = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String time;
        for (OrderProduct orderProduct : DB.ORDER_PRODUCT) {
            for (Order order : DB.ORDERS) {
                if ( order.getUserId().equals(telegramUser.getChatId()) && orderProduct.getOrderId().equals(order.getId()) ) {
                    Product product = findProductById(orderProduct.getProductId());
                    assert product != null;
                    time = order.getLocalDateTime().format(formatter);
                    sum += product.getPrice() * orderProduct.getAmount();
                    str.append(i++).append(". ").append(product.getName()).append(" ").append(product.getPrice()).append(" sum").append(" x ").append(orderProduct.getAmount()).append(" ta\n").append(time).append("\n");
                }
            }
        }
        str.append("\n jami: ").append(sum).append(" sum\n");

        SendMessage sendMessage = new SendMessage(telegramUser.getChatId(), str.toString());
        sendMessage.replyMarkup(new InlineKeyboardMarkup(new InlineKeyboardButton(telegramUser.getText("BACK")).callbackData("back")));
        SendResponse execute = MyBot.telegramBot.execute(sendMessage);
        DB.deletedMessages.add(execute.message().messageId());
    }

    private static Product findProductById(UUID productId) {
        for (Product product : DB.PRODUCTS) {
            if ( product.getId().equals(productId) ) return product;
        }
        return null;
    }
}