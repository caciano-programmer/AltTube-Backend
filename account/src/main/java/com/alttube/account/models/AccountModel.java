package com.alttube.account.models;

import lombok.*;

import javax.persistence.*;


@ToString
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "Account")
public class AccountModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Account_ID")
    private Long Account_ID;

    @Column(name = "Name", nullable = false, updatable = false)
    private String name;

    @Column(name = "Email", nullable = false, unique = true, updatable = false)
    private String email;
    
    @Column(name = "Password", nullable = false, updatable = false)
    private String password;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private AccountExtrasModel accountExtras;

    public AccountModel(String name) { this.name = name; }

    public AccountModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AccountModel(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}