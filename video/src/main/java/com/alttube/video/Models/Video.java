package com.alttube.video.Models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@NoArgsConstructor
@ToString
@Setter
@Getter
@Entity
@Table(name = "Video")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, updatable = false)
    private Long owner;

    @Column(nullable = false, updatable = false)
    private String vidRef;

    @Column(nullable = false, updatable = false)
    private String imgRef;

    @NotNull
    @Column(nullable = false, updatable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private String title;

    @Lob
    @NotNull
    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private final Date date = new Date();

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Transient
    private byte[] image;
}
