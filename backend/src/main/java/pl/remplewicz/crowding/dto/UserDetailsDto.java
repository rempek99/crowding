package pl.remplewicz.crowding.dto;/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * Created by:
 * Name: Arkadiusz Remplewicz
 * Index Number: 224413
 * E-mail: arkadiusz.remplewicz@gmail.com
 * Git-Hub Username: rempek99
 */

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDetailsDto {

    private String username;
    private String firstname;
    private String surname;
    private String gender;
    private Integer age;
}
