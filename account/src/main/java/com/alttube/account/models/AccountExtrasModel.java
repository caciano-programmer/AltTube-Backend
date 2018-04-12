package com.alttube.account.models;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AccountExtrasModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Lob
    private String description;
    private String[] myVideos;
    private String[] likedVideos;
    private String imageReference;
}
