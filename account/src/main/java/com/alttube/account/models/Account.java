package com.alttube.account.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    private String name;
    private String email;
    private String password;

    @Lob
    private String description;
    private String myVideos;
    private String[] likedVideos;
    private String imageReference;
}