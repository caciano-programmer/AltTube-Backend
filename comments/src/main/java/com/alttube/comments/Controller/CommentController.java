package com.alttube.comments.Controller;

import com.alttube.comments.Models.Comment;
import com.alttube.comments.Models.Reply;
import com.alttube.comments.Repository.CommentRepository;
import com.alttube.comments.Services.AdvancedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
public class CommentController {

    private final CommentRepository commentRepository;
    private final AdvancedQuery advancedQuery;

    @Autowired
    public CommentController(CommentRepository commentRepository, AdvancedQuery advancedQuery) {
        this.commentRepository = commentRepository;
        this.advancedQuery = advancedQuery;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/video/comment")
    public Mono<Void> postComment(@Valid Mono<Comment> comment) { return commentRepository.insert(comment).then(); }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/video/reply")
    public Mono<Void> postReply(@Valid Reply reply) { return advancedQuery.addReply(reply.getCommentRef(), reply).then(); }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping(value = "/video/{id}")
    public Flux<Comment> getComments(@PathVariable String id) { return commentRepository.findTop15ByVideoRefOrderByTimestamp(id); }
}
