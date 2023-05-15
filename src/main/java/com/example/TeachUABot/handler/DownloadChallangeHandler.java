package com.example.TeachUABot.handler;

import com.example.TeachUABot.enums.ConversationState;
import com.example.TeachUABot.keyboard.KeyboardHelper;
import com.example.TeachUABot.model.ChallengeModel;
import com.example.TeachUABot.model.UserRequest;
import com.example.TeachUABot.service.ChallengeService;
import com.example.TeachUABot.service.TelegramService;
import com.example.TeachUABot.service.UserSessionService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.io.IOException;

@Component
public class DownloadChallangeHandler extends UserRequestHandler{

    private final TelegramService telegramService;
    private final KeyboardHelper keyboardHelper;
    private final UserSessionService userSessionService;
    private final ChallengeService challengeService;

    public DownloadChallangeHandler(TelegramService telegramService, KeyboardHelper keyboardHelper, UserSessionService userSessionService, ChallengeService challengeService) {
        this.telegramService = telegramService;
        this.keyboardHelper = keyboardHelper;
        this.userSessionService = userSessionService;
        this.challengeService = challengeService;
    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        return isTextMessage(userRequest.getUpdate())
                && ConversationState.WAITING_FOR_CERTIFICATE.equals(userRequest.getUserSession().getState());
    }

    @Override
    public void handle(UserRequest userRequest) {
        ChallengeModel challengeModel = new ChallengeModel();
        String challengeInfo = userRequest.getUpdate().getMessage().getText();
        ReplyKeyboard replyKeyboard = keyboardHelper.buildMenu();
        try {
            challengeInfo = ChallengeService.getChallenge(Long.valueOf(challengeInfo), challengeModel);
            telegramService.sendMessage(userRequest.getChatId(), challengeInfo,replyKeyboard);
            userRequest.getUserSession().setState(ConversationState.START);

        } catch (IOException e) {
            telegramService.sendMessage(userRequest.getChatId(), "челендж з заданим id не було знайдено'" + challengeInfo + "'",replyKeyboard);
            userRequest.getUserSession().setState(ConversationState.START);
        }
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
