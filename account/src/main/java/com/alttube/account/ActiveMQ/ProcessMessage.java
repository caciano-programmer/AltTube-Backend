package com.alttube.account.ActiveMQ;

import com.alttube.account.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import javax.jms.TextMessage;
import javax.validation.Valid;

@Component
public class ProcessMessage {

    private final String Authenticated = "Authenticated";
    private final String NotAuthenticated = "Authentication failed, please log in.";

    private final SecurityService securityService;

    @Autowired
    public ProcessMessage(SecurityService securityService) {
        this.securityService = securityService;
    }

    @JmsListener(destination = "CommentAuthentication")
    @SendTo("Comment")
    public String CommentAuthenticate(@Payload @Valid final TextMessage message) {
        return isAuthenticated(message) ? Authenticated : NotAuthenticated;
    }

    @JmsListener(destination = "VideoAuthentication")
    @SendTo("Video")
    public String VideoAuthenticate(@Payload @Valid final TextMessage message) {
        return isAuthenticated(message) ? Authenticated : NotAuthenticated;
    }

    private boolean isAuthenticated(TextMessage message) {
        try {
            String[] credentials = message.getText().split(" ");
            return securityService.isAuthenticated(credentials[0], credentials[1], credentials[2]);
        } catch (Exception ex) { return false; }
    }
}
