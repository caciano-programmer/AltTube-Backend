package com.alttube.comments.Models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Document
public class Reply {

    @Id
    private String id;

    @NotNull
    private String parentId;

    @NotNull
    private String author;

    @NotNull
    private String text;

    @CreatedDate
    private Date timestamp;
}
