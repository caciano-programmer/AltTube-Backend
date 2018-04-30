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
        if(accountExtrasModel.getAge() != null) this.accountExtras.setAge(accountExtrasModel.getAge());
        if(accountExtrasModel.getGender() != null) this.accountExtras.setGender(accountExtrasModel.getGender());
        return this;
    }
}