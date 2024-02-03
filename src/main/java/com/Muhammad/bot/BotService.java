package com.Muhammad.bot;

import com.Muhammad.db.DB;
import com.Muhammad.entity.OrderProduct;
import com.Muhammad.entity.Product;
import com.Muhammad.entity.TelegramUser;
import com.Muhammad.enums.Language;
import com.Muhammad.enums.TelegramState;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Contact;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ReplyKeyboardRemove;
import com.pengrad.telegrambot.request.DeleteMessages;
import com.pengrad.telegrambot.request.EditMessageReplyMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.SendResponse;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class BotService {
    public static MyBot myBot = new MyBot();

    public static void acceptStartAskLanguage(Message message, TelegramUser telegramUser) {
        SendMessage sendMessage = new SendMessage(message.chat().id(), """
         Assalomu aleykum. Botga hush kelibsiz. Iltimos tilni tanlang!
                   
         Hi, welcome to the bot. Pleas select language to continue!
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
            name += " " + telegramUser.getLastName();
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
        SendMessage sendMessage = new SendMessage(
         telegramUser.getChatId(),
         telegramUser.getText("CLOTHES"));

        // to remove previous buttons
        sendMessage.replyMarkup(new ReplyKeyboardRemove());

        sendMessage.replyMarkup(BotUtils.generateClothesBtns(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendMessage);
        DB.deletedMessages.add(execute.message().messageId());
    }

    public static void showCategoryConsumables(TelegramUser telegramUser) {
        SendMessage sendMessage = new SendMessage(
         telegramUser.getChatId(),
         telegramUser.getText("CONSUMABLES")
        );

        // to remove previous buttons
        sendMessage.replyMarkup(new ReplyKeyboardRemove());

        sendMessage.replyMarkup(BotUtils.generateConsumablesBtns(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendMessage);
        DB.deletedMessages.add(execute.message().messageId());
    }

    public static void showCategoryDrinks(TelegramUser telegramUser) {
        SendMessage sendMessage = new SendMessage(
         telegramUser.getChatId(),
         telegramUser.getText("DRINKS")
        );

        sendMessage.replyMarkup(new ReplyKeyboardRemove());
        sendMessage.replyMarkup(BotUtils.generateDrinksBtns(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendMessage);
        DB.deletedMessages.add(execute.message().messageId());
    }

    public static void showCategorySnacks(TelegramUser telegramUser) {
        SendMessage sendMessage = new SendMessage(
         telegramUser.getChatId(),
         telegramUser.getText("SNACKS")
        );

        sendMessage.replyMarkup(new ReplyKeyboardRemove());
        sendMessage.replyMarkup(BotUtils.generateSnacksBtns(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendMessage);
        DB.deletedMessages.add(execute.message().messageId());
    }

    public static void showProductJumper(TelegramUser telegramUser) {
        SendPhoto sendPhoto = new SendPhoto(
         telegramUser.getChatId(),
         new File("src/main/java/com/Muhammad/photos/jumper.png")
        );

        sendPhoto.replyMarkup(new ReplyKeyboardRemove());
        sendPhoto.caption(telegramUser.getText("JUMPER") + " 100,000 sum");
        sendPhoto.replyMarkup(new ReplyKeyboardRemove());
        sendPhoto.replyMarkup(BotUtils.generateCounter(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendPhoto);
        DB.deletedMessages.add(execute.message().messageId());
        telegramUser.setMessageId(execute.message().messageId());
    }

    public static void showProductTshirt(TelegramUser telegramUser) {
        SendPhoto sendPhoto = new SendPhoto(
         telegramUser.getChatId(),
         new File("src/main/java/com/Muhammad/photos/t-shirt.png")
        );
        sendPhoto.caption(telegramUser.getText("T-SHIRT") + " 80,000  sum");
        sendPhoto.replyMarkup(BotUtils.generateCounter(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendPhoto);
        DB.deletedMessages.add(execute.message().messageId());
        telegramUser.setMessageId(execute.message().messageId());
    }

    public static void showProductTrousers(TelegramUser telegramUser) {
        SendPhoto sendPhoto = new SendPhoto(
         telegramUser.getChatId(),
         new File("src/main/java/com/Muhammad/photos/trousers.png")
        );
        sendPhoto.caption(telegramUser.getText("TROUSERS") + " 90,000 sum");

        sendPhoto.replyMarkup(BotUtils.generateCounter(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendPhoto);
        DB.deletedMessages.add(execute.message().messageId());
        telegramUser.setMessageId(execute.message().messageId());
    }

    public static void showProductAirJordans(TelegramUser telegramUser) {
        SendPhoto sendPhoto = new SendPhoto(
         telegramUser.getChatId(),
         new File("src/main/java/com/Muhammad/photos/air jordans.png")
        );
        sendPhoto.caption(telegramUser.getText("AIR_JORDANS") + " 300,000 sum");
        sendPhoto.replyMarkup(BotUtils.generateCounter(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendPhoto);
        DB.deletedMessages.add(execute.message().messageId());
        telegramUser.setMessageId(execute.message().messageId());
    }

    public static void showProductBanana(TelegramUser telegramUser) {
        SendPhoto sendPhoto = new SendPhoto(
         telegramUser.getChatId(),
         new File("src/main/java/com/Muhammad/photos/Screenshot 2024-02-02 at 7.44.51 PM.png")
        );
        sendPhoto.caption(telegramUser.getText("BANANA") + " 20,000 sum");
        sendPhoto.replyMarkup(BotUtils.generateCounter(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendPhoto);
        DB.deletedMessages.add(execute.message().messageId());
        telegramUser.setMessageId(execute.message().messageId());
    }

    public static void showProductApples(TelegramUser telegramUser) {
        SendPhoto sendPhoto = new SendPhoto(
         telegramUser.getChatId(),
         new File("src/main/java/com/Muhammad/photos/Screenshot 2024-02-02 at 7.44.24 PM.png")
        );
        sendPhoto.caption(telegramUser.getText("APPLES") + " 30,000 sum");
        sendPhoto.replyMarkup(BotUtils.generateCounter(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendPhoto);
        DB.deletedMessages.add(execute.message().messageId());
        telegramUser.setMessageId(execute.message().messageId());
    }

    public static void showProductMeat(TelegramUser telegramUser) {
        SendPhoto sendPhoto = new SendPhoto(
         telegramUser.getChatId(),
         new File("src/main/java/com/Muhammad/photos/Screenshot 2024-02-02 at 7.47.53 PM.png")
        );
        sendPhoto.caption(telegramUser.getText("MEAT") + " 100,000 sum");
        sendPhoto.replyMarkup(BotUtils.generateCounter(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendPhoto);
        DB.deletedMessages.add(execute.message().messageId());
        telegramUser.setMessageId(execute.message().messageId());
    }

    public static void showProductWatermelon(TelegramUser telegramUser) {
        SendPhoto sendPhoto = new SendPhoto(
         telegramUser.getChatId(),
         new File("src/main/java/com/Muhammad/photos/watermelon.png")
        );
        sendPhoto.caption(telegramUser.getText("WATERMELON") + " 15,000 sum");
        sendPhoto.replyMarkup(BotUtils.generateCounter(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendPhoto);
        DB.deletedMessages.add(execute.message().messageId());
        telegramUser.setMessageId(execute.message().messageId());
    }

    public static void showProductCake(TelegramUser telegramUser) {
        SendPhoto sendPhoto = new SendPhoto(
         telegramUser.getChatId(),
         new File("src/main/java/com/Muhammad/photos/Screenshot 2024-02-02 at 7.45.14 PM.png")
        );
        sendPhoto.caption(telegramUser.getText("CAKE") + " 60,000 sum");
        sendPhoto.replyMarkup(BotUtils.generateCounter(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendPhoto);
        DB.deletedMessages.add(execute.message().messageId());
        telegramUser.setMessageId(execute.message().messageId());
    }

    public static void showProductPepsi(TelegramUser telegramUser) {
        SendPhoto sendPhoto = new SendPhoto(
         telegramUser.getChatId(),
         new File("src/main/java/com/Muhammad/photos/pepsi.png")
        );
        sendPhoto.caption(telegramUser.getText("PEPSI") + " 15,000 sum");
        sendPhoto.replyMarkup(BotUtils.generateCounter(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendPhoto);
        DB.deletedMessages.add(execute.message().messageId());
        telegramUser.setMessageId(execute.message().messageId());
    }

    public static void showProductWater(TelegramUser telegramUser) {
        SendPhoto sendPhoto = new SendPhoto(
         telegramUser.getChatId(),
         new File("src/main/java/com/Muhammad/photos/water.png")
        );
        sendPhoto.caption(telegramUser.getText("WATER") + " 7,000 sum");
        sendPhoto.replyMarkup(BotUtils.generateCounter(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendPhoto);
        DB.deletedMessages.add(execute.message().messageId());
        telegramUser.setMessageId(execute.message().messageId());
    }

    public static void showProductSprite(TelegramUser telegramUser) {
        SendPhoto sendPhoto = new SendPhoto(
         telegramUser.getChatId(),
         new File("src/main/java/com/Muhammad/photos/sprite.png")
        );
        sendPhoto.caption(telegramUser.getText("SPRITE") + " 12,000 sum");
        sendPhoto.replyMarkup(BotUtils.generateCounter(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendPhoto);
        DB.deletedMessages.add(execute.message().messageId());
        telegramUser.setMessageId(execute.message().messageId());
    }

    public static void showProductCoke(TelegramUser telegramUser) {
        SendPhoto sendPhoto = new SendPhoto(
         telegramUser.getChatId(),
         new File("src/main/java/com/Muhammad/photos/coke.png")
        );
        sendPhoto.caption(telegramUser.getText("COKE") + " 12,000 sum");
        sendPhoto.replyMarkup(BotUtils.generateCounter(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendPhoto);
        DB.deletedMessages.add(execute.message().messageId());
        telegramUser.setMessageId(execute.message().messageId());
    }

    public static void showProductChocolate(TelegramUser telegramUser) {
        SendPhoto sendPhoto = new SendPhoto(
         telegramUser.getChatId(),
         new File("src/main/java/com/Muhammad/photos/chocolate.png")
        );
        sendPhoto.caption(telegramUser.getText("CHOCOLATE") + " 30,000 sum");
        sendPhoto.replyMarkup(BotUtils.generateCounter(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendPhoto);
        DB.deletedMessages.add(execute.message().messageId());
        telegramUser.setMessageId(execute.message().messageId());
    }

    public static void showProductIceCream(TelegramUser telegramUser) {
        SendPhoto sendPhoto = new SendPhoto(
         telegramUser.getChatId(),
         new File("src/main/java/com/Muhammad/photos/ice cream.png")
        );
        sendPhoto.caption(telegramUser.getText("ICE_CREAM") + " 10,000 sum");
        sendPhoto.replyMarkup(BotUtils.generateCounter(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendPhoto);
        DB.deletedMessages.add(execute.message().messageId());
        telegramUser.setMessageId(execute.message().messageId());
    }

    public static void showProductHoney(TelegramUser telegramUser) {
        SendPhoto sendPhoto = new SendPhoto(
         telegramUser.getChatId(),
         new File("src/main/java/com/Muhammad/photos/honey.png")
        );
        sendPhoto.caption(telegramUser.getText("HONEY") + " 70,000 sum");
        sendPhoto.replyMarkup(BotUtils.generateCounter(telegramUser));
        SendResponse execute = MyBot.telegramBot.execute(sendPhoto);
        DB.deletedMessages.add(execute.message().messageId());
        telegramUser.setMessageId(execute.message().messageId());
    }

    public static void dealWithEachProduct(TelegramUser telegramUser, CallbackQuery callbackQuery) {
        String data = callbackQuery.data();
        if ( data.equals("plus") ) {
            telegramUser.setCounter(telegramUser.getCounter() + 1);
        } else if ( data.equals("minus") && telegramUser.getCounter() > 0 ) {
            telegramUser.setCounter(telegramUser.getCounter() - 1);
        } else if ( data.equals("basket") ) {
            clearMessages(telegramUser);
            BotService.basketPage(telegramUser);
            return;
        } else if ( data.equals("back") ) {
            telegramUser.setCounter(0);
            clearMessages(telegramUser);
            BotService.acceptContactShowCategories(telegramUser, null);
            return;
        } else if ( data.equals(telegramUser.getText("SHOW_BASKET")) ) {
            BotService.showBasket(telegramUser);
        }

        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup(
         telegramUser.getChatId(), telegramUser.getMessageId()
        );

        editMessageReplyMarkup.replyMarkup((InlineKeyboardMarkup) BotUtils.generateCounter(telegramUser));
        MyBot.telegramBot.execute(editMessageReplyMarkup);
    }

    private static void basketPage(TelegramUser telegramUser) {
        UUID chosenProductId = telegramUser.getChosenProductId();
        Product product = findChosenProductByUUID(chosenProductId);
        telegramUser.orderProducts.add(makeAnOrderProduct(product, telegramUser));
        telegramUser.setCounter(0);
    }

    private static OrderProduct makeAnOrderProduct(Product product, TelegramUser telegramUser) {
        OrderProduct orderProduct = new OrderProduct(product.getId());
        orderProduct.setAmount(telegramUser.getCounter());
        return orderProduct;
    }

    private static Product findChosenProductByUUID(UUID chosenProductId) {
        for (Product product : DB.PRODUCTS) {
            if ( product.getId().equals(chosenProductId) ) {
                return product;
            }
        }
        return null;
    }

    public static void clearMessages(TelegramUser telegramUser) {
        List<Integer> deletedMessagesList = DB.deletedMessages;
        int[] arr = deletedMessagesList.stream().mapToInt(Integer::intValue).toArray();
        DeleteMessages deleteMessages = new DeleteMessages(telegramUser.getChatId(), arr);
        MyBot.telegramBot.execute(deleteMessages);
    }

    public static void showBasket(TelegramUser telegramUser) {
        StringBuilder text = new StringBuilder();
        for (OrderProduct orderProduct : telegramUser.orderProducts) {
            int amount = orderProduct.getAmount();
            Product product = findProductById(orderProduct.getOrderId());
            text.append(product.getName()).append(" ").append(amount).append(" ta\n");
        }

        SendMessage sendMessage = new SendMessage(telegramUser.getChatId(),
         telegramUser.getText("YOUR_BASKET") + "\n" + text
        );
    }

    private static Product findProductById(UUID orderId) {
        for (Product product : DB.PRODUCTS) {
            if ( product.getId().equals(orderId) ) {
                return product;
            }
        }
        return null;
    }
}
