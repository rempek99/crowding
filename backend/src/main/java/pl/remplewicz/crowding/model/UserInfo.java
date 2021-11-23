package pl.remplewicz.crowding.model;/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * Created by:
 * Name: Arkadiusz Remplewicz
 * Index Number: 224413
 * E-mail: arkadiusz.remplewicz@gmail.com
 * Git-Hub Username: rempek99
 */

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
public class UserInfo {

    public enum Gender{
        MALE, FEMALE, OTHER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;

    private String surname;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne
    private User user;

}
