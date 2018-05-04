package com.alttube.comments.Services;

import com.alttube.comments.Models.Comment;
import com.alttube.comments.Models.Reply;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AdvancedQueryImpl implements AdvancedQuery {

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Override
    public Mono<UpdateResult> addReply(String id, Reply reply) {
        return reactiveMongoTemplate.updateFirst(
                new Query().addCriteria(Criteria.where("_id").is(id)),
                new Update().push("replies", reply),
                Comment.class
        );
    }
}
