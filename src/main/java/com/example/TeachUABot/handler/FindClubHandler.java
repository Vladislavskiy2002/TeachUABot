package com.example.TeachUABot.handler;

import com.example.TeachUABot.enums.ConversationState;
import com.example.TeachUABot.keyboard.KeyboardHelper;
import com.example.TeachUABot.model.ClubModel;
import com.example.TeachUABot.model.UserRequest;
import com.example.TeachUABot.service.ClubService;
import com.example.TeachUABot.service.TelegramService;
import com.example.TeachUABot.service.UserSessionService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.io.IOException;


@Component
public class FindClubHandler extends UserRequestHandler{

    private final TelegramService telegramService;
    private final KeyboardHelper keyboardHelper;
    private final UserSessionService userSessionService;
    private final ClubService clubService;

    public FindClubHandler(TelegramService telegramService, KeyboardHelper keyboardHelper, UserSessionService userSessionService, ClubService clubService) {
        this.telegramService = telegramService;
        this.keyboardHelper = keyboardHelper;
        this.userSessionService = userSessionService;
        this.clubService = clubService;
    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        return isTextMessage(userRequest.getUpdate())
                && ConversationState.WAITING_FOR_CLUB.equals(userRequest.getUserSession().getState());
    }

    @Override
    public void handle(UserRequest userRequest) {
        ClubModel clubModel = new ClubModel();
        String clubInfo = userRequest.getUpdate().getMessage().getText();
        ReplyKeyboard replyKeyboard = keyboardHelper.buildMenu();
        try {
            clubInfo = ClubService.getClub(Long.valueOf(clubInfo), clubModel);
            telegramService.sendMessage(userRequest.getChatId(), clubInfo,replyKeyboard);
            userRequest.getUserSession().setState(ConversationState.START);

        } catch (IOException e) {
            telegramService.sendMessage(userRequest.getChatId(), "Групу з id '" + clubInfo + "' небуло знайдено",replyKeyboard);
            userRequest.getUserSession().setState(ConversationState.START);
        }
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
