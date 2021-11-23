package pl.remplewicz.crowding.util.converter;

import pl.remplewicz.crowding.dto.UserDto;
import pl.remplewicz.crowding.model.User;

/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * Created by:
 * Name: Arkadiusz Remplewicz
 * Index Number: 224413
 * E-mail: arkadiusz.remplewicz@gmail.com
 * Git-Hub Username: rempek99
 */

public class UserConverter {

    private UserConverter(){}

    public static UserDto toDto(User entity){
        return UserDto.builder()
                .username(entity.getUsername())
                .password(entity.getPassword())
                .build();
    }

    public static User createEntityFromDto(UserDto userDto) {
        return new User(userDto.getUsername(), userDto.getPassword());
    }
}
