package com.alttube.comments.Services;

import com.alttube.comments.Models.Reply;
import com.mongodb.client.result.UpdateResult;
import reactor.core.publisher.Mono;

public interface AdvancedQuery {

    Mono<UpdateResult> addReply(String id, Reply reply);
}
