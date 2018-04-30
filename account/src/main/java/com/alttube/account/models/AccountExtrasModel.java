package com.alttube.account.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
    private Long ID;

    @OneToOne
    @MapsId
    private AccountModel accountModel;

    @Lob
    private String description;
    private String gender;
    private Integer age;
    private String imageName;
}
