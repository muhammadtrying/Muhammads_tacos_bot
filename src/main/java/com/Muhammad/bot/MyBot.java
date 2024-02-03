package com.Muhammad.bot;

import com.Muhammad.db.DB;
import com.Muhammad.entity.Product;
import com.Muhammad.entity.TelegramUser;
import com.Muhammad.enums.Language;
import com.Muhammad.enums.TelegramState;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Contact;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import lombok.SneakyThrows;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyBot {

    // Creating a telegram bot object so, it can be used in any class to execute orders.
    public static TelegramBot telegramBot = new TelegramBot("6608981322:AAFk4_8DUO5na3gGWJhVNh2hROFYpNuExfE");

    // ExecutorService to respond requests in various threads.
    public static ExecutorService executorService = Executors.newFixedThreadPool(10);

    // catching each request and directing them to handler
    @SneakyThrows
    public void start() {
        telegramBot.setUpdatesListener((updates) -> {
            for (Update update : updates) {
                CompletableFuture.runAsync(() -> handleUpdate(update), executorService);
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    private void handleUpdate(Update update) {

        // anything user sends is considered to be a message except when they press an inline button(i.e. callback query)
        if ( update.message() != null ) {

            Message message = update.message();
            Long chatId = message.chat().id();
            TelegramUser telegramUser = getUser(chatId);

            if ( message.text() != null ) {

                String text = message.text();

                if ( text.equals("/start") ) {
                    BotService.acceptStartAskLanguage(message, telegramUser);
                }


                // showing categories
                else if ( telegramUser.checkState(TelegramState.SHOW_CATEGORIES) ) {
                    showCategories(text, telegramUser, message);
                }


                // showing products
                else if ( telegramUser.checkState(TelegramState.SHOW_PRODUCTS) ) {
                    showProducts(text, telegramUser, message);
                }


                // to get contact
            } else if ( message.contact() != null ) {
                Contact contact = message.contact();
                if ( telegramUser.checkState(TelegramState.SHARE_CONTACT) ) {
                    DB.deletedMessages.add(message.messageId());
                    BotService.acceptContactShowCategories(telegramUser, contact);
                }
            }


            // when pressed a button, this statement works. For instance + button in delivery bots
        } else if ( update.callbackQuery() != null ) {
            workWithQueries(update);
        }
    }

    private void workWithQueries(Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        Long chatId = callbackQuery.from().id();
        TelegramUser telegramUser = getUser(chatId);

        if ( telegramUser.checkState(TelegramState.SELECT_LANG) ) {
            BotService.acceptLanguageAskContact(telegramUser, callbackQuery);
        } else if ( telegramUser.checkState(TelegramState.EACH_PRODUCT) ) {
            BotService.dealWithEachProduct(telegramUser, callbackQuery);
        }
    }

    private void showCategories(String text, TelegramUser telegramUser, Message message) {
        DB.deletedMessages.add(message.messageId());
        BotService.clearMessages(telegramUser);
        if ( text.equals(telegramUser.getText("CLOTHES")) ) {
            BotService.showCategoryClothes(telegramUser);
        }
        if ( text.equals(telegramUser.getText("CONSUMABLES")) ) {
            BotService.showCategoryConsumables(telegramUser);
        }
        if ( text.equals(telegramUser.getText("DRINKS")) ) {
            BotService.showCategoryDrinks(telegramUser);
        }
        if ( text.equals(telegramUser.getText("SNACKS")) ) {
            BotService.showCategorySnacks(telegramUser);
        }
        if ( text.equals(telegramUser.getText("CHOOSE_LANG")) ) {
            BotService.acceptStartAskLanguage(message, telegramUser);
            return;
        }
        telegramUser.setTelegramState(TelegramState.SHOW_PRODUCTS);
    }

    public void showProducts(String text, TelegramUser telegramUser, Message message) {
        telegramUser.setCounter(0);
        DB.deletedMessages.add(message.messageId());
        BotService.clearMessages(telegramUser);
        telegramUser.setTelegramState(TelegramState.EACH_PRODUCT);

        if ( text.equals(telegramUser.getText("JUMPER")) ) {
            BotService.showProductJumper(telegramUser);
        }
        if ( text.equals(telegramUser.getText("T-SHIRT")) ) {
            BotService.showProductTshirt(telegramUser);
        }
        if ( text.equals(telegramUser.getText("TROUSERS")) ) {
            BotService.showProductTrousers(telegramUser);
        }
        if ( text.equals(telegramUser.getText("AIR_JORDANS")) ) {
            BotService.showProductAirJordans(telegramUser);
        }
        if ( text.equals(telegramUser.getText("BANANA")) ) {
            BotService.showProductBanana(telegramUser);
        }
        if ( text.equals(telegramUser.getText("APPLES")) ) {
            BotService.showProductApples(telegramUser);
        }
        if ( text.equals(telegramUser.getText("MEAT")) ) {
            BotService.showProductMeat(telegramUser);
        }
        if ( text.equals(telegramUser.getText("WATERMELON")) ) {
            BotService.showProductWatermelon(telegramUser);
        }
        if ( text.equals(telegramUser.getText("COKE")) ) {
            BotService.showProductCoke(telegramUser);
        }
        if ( text.equals(telegramUser.getText("PEPSI")) ) {
            BotService.showProductPepsi(telegramUser);
        }
        if ( text.equals(telegramUser.getText("WATER")) ) {
            BotService.showProductWater(telegramUser);
        }
        if ( text.equals(telegramUser.getText("SPRITE")) ) {
            BotService.showProductSprite(telegramUser);
        }
        if ( text.equals(telegramUser.getText("CAKE")) ) {
            BotService.showProductCake(telegramUser);
        }
        if ( text.equals(telegramUser.getText("CHOCOLATE")) ) {
            BotService.showProductChocolate(telegramUser);
        }
        if ( text.equals(telegramUser.getText("ICE_CREAM")) ) {
            BotService.showProductIceCream(telegramUser);
        }
        if ( text.equals(telegramUser.getText("HONEY")) ) {
            BotService.showProductHoney(telegramUser);
        }
        telegramUser.setChosenProductId(findChosenProductIdByName(text, telegramUser));
    }

    private UUID findChosenProductIdByName(String text, TelegramUser telegramUser) {
        boolean switchLanguage = false;
        if ( telegramUser.getLanguage() != Language.EN ) {
            telegramUser.setLanguage(Language.EN);
            switchLanguage = true;
        }

        String targetProductName = telegramUser.getText(text);
        if ( targetProductName != null ) {
            targetProductName = targetProductName.toLowerCase();

            for (Product product : DB.PRODUCTS) {
                if ( product.getName().toLowerCase().equals(targetProductName) ) {
                    return product.getId();
                }
            }
        }

        if ( switchLanguage ) {
            telegramUser.setLanguage(Language.UZ);
        }

        return null;
    }


    private TelegramUser getUser(Long chatId) {
        TelegramUser searchedUser = DB.TELEGRAM_USERS.getOrDefault(chatId, null);
        if ( searchedUser != null ) return searchedUser;
        else {
            TelegramUser newUser = TelegramUser.builder()
             .chatId(chatId)
             .telegramState(TelegramState.START)
             .build();
            DB.TELEGRAM_USERS.put(chatId, newUser);
            return newUser;
        }
    }
}
