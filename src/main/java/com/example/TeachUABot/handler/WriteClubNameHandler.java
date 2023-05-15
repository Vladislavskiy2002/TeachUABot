package com.example.TeachUABot.handler;


import com.example.TeachUABot.enums.ConversationState;
import com.example.TeachUABot.keyboard.KeyboardHelper;
import com.example.TeachUABot.model.UserRequest;
import com.example.TeachUABot.model.UserSession;
import com.example.TeachUABot.service.ChallengeService;
import com.example.TeachUABot.service.TelegramService;
import com.example.TeachUABot.service.UserSessionService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import static com.example.TeachUABot.constant.Constants.BTN_FIND_CLUB;

@Component
public class WriteClubNameHandler extends UserRequestHandler{

    private final TelegramService telegramService;
    private final KeyboardHelper keyboardHelper;
    private final UserSessionService userSessionService;

    public WriteClubNameHandler(TelegramService telegramService, KeyboardHelper keyboardHelper, UserSessionService userSessionService) {
        this.telegramService = telegramService;
        this.keyboardHelper = keyboardHelper;
        this.userSessionService = userSessionService;
    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        return isTextMessage(userRequest.getUpdate(), BTN_FIND_CLUB);
    }

    @Override
    public void handle(UserRequest userRequest) {
        ReplyKeyboard replyKeyboard = keyboardHelper.buildWriteMenu();
        telegramService.sendMessage(userRequest.getChatId(), "Введіть id групи:",replyKeyboard);
        UserSession session = userRequest.getUserSession();
        session.setState(ConversationState.WAITING_FOR_CLUB);
        session.setText(userRequest.getUserSession().getText());
        userSessionService.saveSession(userRequest.getChatId(), session);
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}


