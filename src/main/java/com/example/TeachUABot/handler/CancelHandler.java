package com.example.TeachUABot.handler;
import com.example.TeachUABot.enums.ConversationState;
import com.example.TeachUABot.keyboard.KeyboardHelper;
import com.example.TeachUABot.model.UserRequest;
import com.example.TeachUABot.model.UserSession;
import com.example.TeachUABot.service.TelegramService;
import com.example.TeachUABot.service.UserSessionService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import static com.example.TeachUABot.constant.Constants.BTN_CANCEL;

@Component
public class CancelHandler extends UserRequestHandler {

    private final TelegramService telegramService;
    private final KeyboardHelper keyboardHelper;
    private final UserSessionService userSessionService;

    public CancelHandler(TelegramService telegramService, KeyboardHelper keyboardHelper, UserSessionService userSessionService) {
        this.telegramService = telegramService;
        this.keyboardHelper = keyboardHelper;
        this.userSessionService = userSessionService;
    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        return isTextMessage(userRequest.getUpdate(), BTN_CANCEL);
    }

    @Override
    public void handle(UserRequest userRequest) {
        ReplyKeyboard replyKeyboard = keyboardHelper.buildMenu();
        telegramService.sendMessage(userRequest.getChatId(),
                "Обирайте з меню нижче ⤵️", replyKeyboard);

        UserSession userSession = userRequest.getUserSession();
        userSession.setState(ConversationState.CONVERSATION_STARTED);
        userSessionService.saveSession(userSession.getChatId(), userSession);
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}