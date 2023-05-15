package com.example.TeachUABot.bot;

import com.example.TeachUABot.config.BotConfig;
import com.example.TeachUABot.dispatcher.Dispatcher;
import com.example.TeachUABot.keyboard.KeyboardHelper;
import com.example.TeachUABot.model.ChallengeModel;
import com.example.TeachUABot.model.UserRequest;
import com.example.TeachUABot.model.UserSession;
import com.example.TeachUABot.service.UserSessionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@AllArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    private final BotConfig botConfig;
    private final Dispatcher dispatcher;
    private final UserSessionService userSessionService;
    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            UserSession session = userSessionService.getSession(chatId);
            UserRequest userRequest = UserRequest
                    .builder()
                    .update(update)
                    .userSession(session)
                    .chatId(chatId)
                    .build();

            boolean dispatched = dispatcher.dispatch(userRequest);
        }

    }
}