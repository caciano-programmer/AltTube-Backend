package com.alttube.account.models;

import lombok.*;
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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private AccountExtrasModel accountExtras;

    public AccountModel addExtras(AccountExtrasModel accountExtrasModel) {
        this.setAccountExtras(accountExtrasModel);
        return this;
    }
}