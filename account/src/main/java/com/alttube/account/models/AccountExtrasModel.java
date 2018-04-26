package com.alttube.account.models;

import lombok.*;
import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "AccountExtras")
public class AccountExtrasModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Account_ID;

    @OneToOne
    @MapsId
    private AccountModel accountModel;

    @Lob
    private String description;
    private String[] myVideos;
    private String[] likedVideos;
    private String imageReference;
}
