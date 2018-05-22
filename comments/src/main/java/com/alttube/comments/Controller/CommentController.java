package com.alttube.comments.Controller;

import com.alttube.comments.Models.Comment;
import com.alttube.comments.Models.Reply;
import com.alttube.comments.Repository.CommentRepository;
import com.alttube.comments.Services.AdvancedQuery;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.jms.JMSException;
import javax.validation.Valid;

@RestController
public class CommentController {

    private final CommentRepository commentRepository;
    private final AdvancedQuery advancedQuery;
    private final JmsTemplate jmsTemplate;

    @Autowired
    public CommentController(CommentRepository commentRepository, AdvancedQuery advancedQuery, JmsTemplate jmsTemplate) {
        this.commentRepository = commentRepository;
        this.advancedQuery = advancedQuery;
        this.jmsTemplate = jmsTemplate;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/video/comment")
     public Mono<Void> postComment(@Valid Mono<Comment> comment,
                                  @RequestHeader(name = "token", defaultValue = "Empty") String headerToken,
                                  @CookieValue(name = "jwt", defaultValue = "Empty") String jwt,
                                  @CookieValue(name = "token", defaultValue = "Empty") String cookieToken) {

        authenticateCredentials(headerToken, cookieToken, jwt);
        return commentRepository.insert(comment).then();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/video/reply")
    public Mono<Void> postReply(@Valid Reply reply,
                                @RequestHeader(name = "token", defaultValue = "Empty") String headerToken,
                                @CookieValue(name = "jwt", defaultValue = "Empty") String jwt,
                                @CookieValue(name = "token", defaultValue = "Empty") String cookieToken) {
        authenticateCredentials(headerToken, cookieToken, jwt);
        return advancedQuery.addReply(reply.getCommentRef(), reply).then();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping(value = "/video/comment/{id}")
    public Flux<Comment> getComments(@PathVariable String id) {
        return commentRepository.findTop15ByVideoRefOrderByTimestamp(id);
    }

    private void authenticateCredentials(String headerToken, String cookieToken, String jwt) {
        String credentials = headerToken + " " + cookieToken + " " + jwt;
        ActiveMQTextMessage msg = (ActiveMQTextMessage)jmsTemplate.sendAndReceive(
                "CommentAuthentication", session -> session.createTextMessage(credentials) );
        try {
            if(!msg.getText().equals("Authenticated")) throw new RuntimeException(msg.getText());
        } catch (JMSException ex) { throw new RuntimeException("Server Error!, ApacheMQ failure, please contact administrator"); }
    }
}
