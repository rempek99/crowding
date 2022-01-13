package pl.remplewicz.crowding.util.converter;

import pl.remplewicz.crowding.dto.UserDetailsDto;
import pl.remplewicz.crowding.dto.UserDto;
import pl.remplewicz.crowding.model.User;
import pl.remplewicz.crowding.model.UserInfo;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

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

    private UserConverter() {
    }

    public static UserDto toDto(User entity) {
        return UserDto.builder()
                .username(entity.getUsername())
                .password(entity.getPassword())
                .build();
    }

    public static User createEntityFromDto(UserDto userDto) {
        return new User(userDto.getUsername(), userDto.getPassword());
    }

    public static UserDetailsDto toDtoWithDetails(User user) {
        UserInfo userInfo = user.getUserInfo();
        if (userInfo == null) {
            return UserDetailsDto.builder().username(user.getUsername()).build();
        }
        return UserDetailsDto.builder()
                .username(user.getUsername())
                .firstname(userInfo.getFirstname())
                .surname(userInfo.getSurname())
                .age(userInfo.getAge())
                .gender(userInfo.getGender().name())
                .build();
    }

    public static Set<UserDetailsDto> toSetDtoWithDetails(Set<User> user) {
        return user.stream().map(UserConverter::toDtoWithDetails).collect(Collectors.toSet());
    }

    public static UserInfo createUserInfoFromDto(UserDetailsDto newDetails, User user) {
        Long id = null;
        if (user.getUserInfo() != null) {
            id = user.getUserInfo().getId();
        }
        return new UserInfo(id, newDetails.getFirstname(), newDetails.getSurname(), newDetails.getAge(),
                UserInfo.Gender.valueOf(newDetails.getGender()), user);
    }
}
