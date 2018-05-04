package com.alttube.comments.Models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Reply {

    @NotNull
    @Transient
    private String commentRef;

    @NotNull
    private String author;

    @NotNull
    private String text;

    private final Date timestamp = new Date();
}
