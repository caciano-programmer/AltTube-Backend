package com.alttube.account.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@ToString
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Account")
public class AccountModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Account_ID;

    @Column(nullable = false, updatable = false)
    private String name;

    @Column(nullable = false, unique = true, updatable = false)
    private String email;
    
    @Column(nullable = false, updatable = false)
    private String password;

    @OneToOne(mappedBy = "accountModel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private AccountExtrasModel accountExtras;

    public AccountModel addExtras(AccountExtrasModel accountExtrasModel) {
        accountExtrasModel.setAccountModel(this);
        this.setAccountExtras(accountExtrasModel);
        return this;
    }

    public AccountModel setExtras(AccountExtrasModel accountExtrasModel) {
        if(accountExtrasModel.getDescription() != null) this.accountExtras.setDescription(accountExtrasModel.getDescription());
        if(accountExtrasModel.getMyVideos() != null) this.accountExtras.setMyVideos(accountExtrasModel.getMyVideos());
        if(accountExtrasModel.getLikedVideos() != null) this.accountExtras.setLikedVideos(accountExtrasModel.getLikedVideos());
        if(accountExtrasModel.getImageReference() != null) this.accountExtras.setImageReference(accountExtrasModel.getImageReference());
        return this;
    }
}