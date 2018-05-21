package com.alttube.video.Models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@ToString
@Setter
@Getter
@Entity
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, updatable = false, unique = true)
    private String owner;

    @NotNull
    @Column(nullable = false, updatable = false)
    private String vidRef;

    @NotNull
    @Column(nullable = false, updatable = false)
    private String imgRef;

    @NotNull
    @Column(nullable = false)
    private String title;

    @Lob
    private String description;

    @Column(nullable = false)
    private final Date date = new Date();

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @ElementCollection
    private Set<String> keywords = new HashSet<>();
}
