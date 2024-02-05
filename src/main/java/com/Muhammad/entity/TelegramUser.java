package com.Muhammad.entity;

import com.Muhammad.db.DB;
import com.Muhammad.enums.Language;
import com.Muhammad.enums.TelegramState;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.*;

@Data
@Builder
public class TelegramUser implements Serializable {
    private Long chatId;
    private Product chosenProduct;
    private Integer messageId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private int counter;
    public Map<Product, Integer> basket;
    private Language language;
    private TelegramState telegramState;

    public static void showUsers() {
        for (Map.Entry<Long, TelegramUser> entry : DB.TELEGRAM_USERS.entrySet()) {
            TelegramUser user = entry.getValue();
            System.out.println(user);
            System.out.println("=====================\n");
        }
    }

    public boolean checkState(TelegramState selectLang) {
        return telegramState.equals(selectLang);
    }

    public String getText(String txt) {

        ResourceBundle resourceBundle = ResourceBundle.getBundle(
         "message", Locale.forLanguageTag(language.toString()));
        return resourceBundle.getString(txt);
    }
}
