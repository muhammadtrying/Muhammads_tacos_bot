package com.Muhammad.entity;

import com.Muhammad.enums.Language;
import com.Muhammad.enums.TelegramState;
import lombok.Builder;
import lombok.Data;

import java.util.Locale;
import java.util.ResourceBundle;

@Data
@Builder
public class TelegramUser {
    private Long chatId;
    private Integer messageId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Integer counter = 0;
    private Language language;
    private TelegramState telegramState;

    public static void showUsers() {
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
