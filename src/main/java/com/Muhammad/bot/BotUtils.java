package com.Muhammad.bot;

import com.Muhammad.entity.Product;
import com.Muhammad.entity.TelegramUser;
import com.Muhammad.enums.Language;
import com.pengrad.telegrambot.model.request.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BotUtils {
    public static Keyboard generateLangBtns() {
        return new InlineKeyboardMarkup(
         new InlineKeyboardButton("🇬🇧 EN").callbackData(Language.EN.toString()),
         new InlineKeyboardButton("🇺🇿 UZ").callbackData(Language.UZ.toString())
        );
    }

    public static Keyboard generateContactBtns(TelegramUser telegramUser) {
        KeyboardButton keyboardButton = new KeyboardButton(telegramUser.getText("SHARE_CONTACT_BTN"));
        keyboardButton.requestContact(true);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(keyboardButton);
        replyKeyboardMarkup.resizeKeyboard(true);

        return replyKeyboardMarkup;
    }

    public static Keyboard generateCategoryButton(TelegramUser telegramUser) {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
         new String[]{
          telegramUser.getText("CLOTHES"), telegramUser.getText("CONSUMABLES")
         },

         new String[]{
          telegramUser.getText("DRINKS"), telegramUser.getText("SNACKS")
         },
         new String[]{
          telegramUser.getText("SEE_BASKET")
         }
        );

        replyKeyboardMarkup.resizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public static Keyboard generateClothesBtns(TelegramUser telegramUser) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(

         new String[]{
          telegramUser.getText("JUMPER"), telegramUser.getText("T-SHIRT")
         },

         new String[]{
          telegramUser.getText("TROUSERS"), telegramUser.getText("AIR_JORDANS")
         },
         new String[]{
          telegramUser.getText("SEE_BASKET")
         }
        );
        replyKeyboardMarkup.resizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public static Keyboard generateConsumablesBtns(TelegramUser telegramUser) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(

         new String[]{
          telegramUser.getText("BANANA"), telegramUser.getText("APPLES")
         },

         new String[]{
          telegramUser.getText("MEAT"), telegramUser.getText("WATERMELON")
         },
         new String[]{
          telegramUser.getText("SEE_BASKET")
         }
        );
        replyKeyboardMarkup.resizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public static Keyboard generateDrinksBtns(TelegramUser telegramUser) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(

         new String[]{
          telegramUser.getText("COKE"), telegramUser.getText("PEPSI")
         },
         new String[]{
          telegramUser.getText("WATER"), telegramUser.getText("SPRITE")
         },
         new String[]{
          telegramUser.getText("SEE_BASKET")
         }
        );
        replyKeyboardMarkup.resizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public static Keyboard generateSnacksBtns(TelegramUser telegramUser) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(

         new String[]{
          telegramUser.getText("CAKE"), telegramUser.getText("CHOCOLATE")
         },
         new String[]{
          telegramUser.getText("ICE_CREAM"), telegramUser.getText("HONEY")
         },
         new String[]{
          telegramUser.getText("SEE_BASKET")
         }
        );
        replyKeyboardMarkup.resizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public static Keyboard generateCounter(TelegramUser user) {
        return new InlineKeyboardMarkup(
         new InlineKeyboardButton(user.getText("BACK")).callbackData("back"),
         new InlineKeyboardButton("-").callbackData("minus"),
         new InlineKeyboardButton(user.getCounter() + "").callbackData("son"),
         new InlineKeyboardButton("+").callbackData("plus"),
         new InlineKeyboardButton(user.getText("BASKET")).callbackData("basket")
        );
    }

    public static Keyboard generateCancelButton(TelegramUser telegramUser) {
        return new InlineKeyboardMarkup(
         new InlineKeyboardButton(telegramUser.getText("CANCEL")));
    }
}

