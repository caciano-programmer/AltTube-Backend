package com.alttube.account.models;

import lombok.*;
import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class AccountExtrasModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Account_ID")
    private Long ID;

    @Lob
    private String description;
    private String[] myVideos;
    private String[] likedVideos;
    private String imageReference;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private AccountModel account;
}
