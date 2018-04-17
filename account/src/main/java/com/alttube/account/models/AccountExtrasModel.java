package com.alttube.account.models;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class AccountExtrasModel {

    @Id
    @Column(name = "Account_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Lob
    private String description;
    private String[] myVideos;
    private String[] likedVideos;
    private String imageReference;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Account_ID")
    private AccountModel account;
}
