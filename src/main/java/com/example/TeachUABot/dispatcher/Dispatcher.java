package com.example.TeachUABot.dispatcher;

import com.example.TeachUABot.handler.UserRequestHandler;
import com.example.TeachUABot.model.UserRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Dispatcher {

    private final List<UserRequestHandler> handlers;

    public Dispatcher(List<UserRequestHandler> handlers) {
        this.handlers = handlers;
    }

    public boolean dispatch(UserRequest userRequest) {
        for (UserRequestHandler userRequestHandler : handlers) {
            if(userRequestHandler.isApplicable(userRequest)){
                userRequestHandler.handle(userRequest);
                return true;
            }
        }
        return false;
    }
}
