package com.alttube.comments.Models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Document
public class Comment {

    @Id
    private String id;

    @NotNull
    private String author;

    @NotNull
    private String text;

    @NotNull
    private String videoRef;

    private final List<Reply> replies = new ArrayList<>();

    private final Date timestamp = new Date();
}
