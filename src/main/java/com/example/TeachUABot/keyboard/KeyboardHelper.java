package com.example.TeachUABot.keyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.TeachUABot.constant.Constants.BTN_CANCEL;

/**
 * Helper class, allows to build keyboards for users
 */
@Component
public class KeyboardHelper {

    public ReplyKeyboardMarkup buildMenu() {
        List<KeyboardButton> buttons = List.of(
                new KeyboardButton("Знайти Групу"),
                new KeyboardButton("Знайти челендж"));
        KeyboardRow row1 = new KeyboardRow(buttons);
        KeyboardRow row2 = new KeyboardRow(List.of(new KeyboardButton(BTN_CANCEL)));
        return ReplyKeyboardMarkup.builder()
                .keyboard(List.of(row1, row2))
                .selective(true)
                .resizeKeyboard(true)
                .oneTimeKeyboard(false)
                .build();
    }

    public ReplyKeyboardMarkup buildWriteMenu() {
        KeyboardRow row2 = new KeyboardRow(List.of(new KeyboardButton(BTN_CANCEL)));
        return ReplyKeyboardMarkup.builder()
                .keyboard(List.of(row2))
                .selective(true)
                .resizeKeyboard(true)
                .oneTimeKeyboard(false)
                .build();
    }

    public ReplyKeyboardMarkup buildMenuWithCancel() {
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(BTN_CANCEL);

        return ReplyKeyboardMarkup.builder()
                .keyboard(List.of(keyboardRow))
                .selective(true)
                .resizeKeyboard(true)
                .oneTimeKeyboard(false)
                .build();
    }
}
