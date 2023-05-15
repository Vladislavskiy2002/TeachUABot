package com.example.TeachUABot.handler;
import com.example.TeachUABot.enums.ConversationState;
import com.example.TeachUABot.keyboard.KeyboardHelper;
import com.example.TeachUABot.model.UserRequest;
import com.example.TeachUABot.service.TelegramService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
@Component
public class StartCommandHandler extends UserRequestHandler {

    private static String command = "/start";

    private final TelegramService telegramService;
    private final KeyboardHelper keyboardHelper;

    public StartCommandHandler(TelegramService telegramService, KeyboardHelper keyboardHelper) {
        this.telegramService = telegramService;
        this.keyboardHelper = keyboardHelper;
    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        return isCommand(userRequest.getUpdate(), command);
    }

    @Override
    public void handle(UserRequest request) {
        ReplyKeyboard replyKeyboard = keyboardHelper.buildMenu();
        telegramService.sendMessage(request.getChatId(),
                "\uD83D\uDC4BПривіт! За допомогою цього чат-бота ви зможете знайти групу або завантажити сертифікат",
                replyKeyboard);
        telegramService.sendMessage(request.getChatId(),
                "Обирайте з меню нижче ⤵️");
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
