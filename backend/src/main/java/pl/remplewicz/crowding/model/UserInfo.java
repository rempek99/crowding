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
    public UserInfo() {
    }

    public UserInfo(Long id,String firstname, String surname, Integer age, Gender gender, User user) {
        this.id = id;
        this.firstname = firstname;
        this.surname = surname;
        this.age = age;
        this.gender = gender;
        this.user = user;
    }

    public enum Gender{
        MALE, FEMALE
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
