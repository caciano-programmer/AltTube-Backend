package com.alttube.account.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "Account", indexes = { @Index(columnList = "Email") })
public class AccountModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Account_ID")
    private Long Account_ID;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Email", nullable = false, unique = true, updatable = false)
    private String email;
    
    @Column(name = "Password", nullable = false, updatable = false)
    private String password;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "account")
    private AccountExtrasModel accountExtras;

    public AccountModel(String name, String email, String password, AccountExtrasModel accountExtras) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.accountExtras = accountExtras;
    }

    public AccountModel(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}