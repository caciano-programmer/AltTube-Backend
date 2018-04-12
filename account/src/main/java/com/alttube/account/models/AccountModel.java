package com.alttube.account.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@Entity
@Table(indexes = { @Index(columnList = "Email", unique = true) })
public class AccountModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Email", nullable = false, unique = true)
    private String email;
    
    @Column(name = "Password", nullable = false)
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private AccountExtrasModel accountExtrasModel;
}