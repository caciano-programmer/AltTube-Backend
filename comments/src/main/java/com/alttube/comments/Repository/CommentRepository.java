package com.alttube.comments.Repository;

import com.alttube.comments.Models.Comment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {

    Flux<Comment> findTop15ByVideoRefOrderByTimestamp(String videoRef);
}
