package com.alttube.comments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@EnableJms
@SpringBootApplication
public class CommentsApplication {

	public static void main(String[] args) { SpringApplication.run(CommentsApplication.class, args); }
}
