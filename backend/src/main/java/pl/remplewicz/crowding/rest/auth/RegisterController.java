package pl.remplewicz.crowding.rest.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.remplewicz.crowding.dto.UserDto;
import pl.remplewicz.crowding.exception.DuplicationException;
import pl.remplewicz.crowding.model.User;
import pl.remplewicz.crowding.service.UserService;
import pl.remplewicz.crowding.util.converter.UserConverter;

import javax.validation.Valid;

/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * Created by:
 * Name: Arkadiusz Remplewicz
 * Index Number: 224413
 * E-mail: arkadiusz.remplewicz@gmail.com
 * Git-Hub Username: rempek99
 */

@RestController
@RequestMapping("api/public")
public class RegisterController {

    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
    public UserDto register(@Valid UserDto userDto) throws DuplicationException {
        User user = UserConverter.createEntityFromDto(userDto);
        user = userService.register(user);
        return UserConverter.toDto(user);
    }
}
