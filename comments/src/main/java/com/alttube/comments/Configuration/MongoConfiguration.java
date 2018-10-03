package com.alttube.comments.Configuration;

import com.mongodb.MongoClientOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfiguration {

    @Bean
    public MongoClientOptions mongoClientOptions() {
        return MongoClientOptions.builder().connectTimeout(1000*240).build();
    }
}
