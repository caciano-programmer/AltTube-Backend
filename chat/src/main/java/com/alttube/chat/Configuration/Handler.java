package com.alttube.chat.Configuration;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;

public class Handler extends TextWebSocketHandler {

    private final ArrayList<WebSocketSession> sessions = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) { sessions.add(session); }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        for(WebSocketSession webSocketSession: sessions)
            if(webSocketSession.isOpen())
                webSocketSession.sendMessage(new TextMessage(message.getPayload()));
    }
}
