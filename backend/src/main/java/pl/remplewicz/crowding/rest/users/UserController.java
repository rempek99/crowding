package pl.remplewicz.crowding.rest.users;/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * Created by:
 * Name: Arkadiusz Remplewicz
 * Index Number: 224413
 * E-mail: arkadiusz.remplewicz@gmail.com
 * Git-Hub Username: rempek99
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.remplewicz.crowding.dto.UserDetailsDto;
import pl.remplewicz.crowding.model.Role;
import pl.remplewicz.crowding.model.User;
import pl.remplewicz.crowding.model.UserInfo;
import pl.remplewicz.crowding.service.UserService;

import javax.annotation.security.PermitAll;
import java.security.Principal;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("details/{id}")
    @PermitAll
    public UserDetailsDto getUserDetails(Principal principal, @PathVariable String id) {
        User user = userService.findById(Long.parseLong(id));
        User caller = userService.findByUsername(principal.getName());
        if (user.getUsername().equals(principal.getName()) || caller.hasRole(Role.ADMIN)) {
            UserInfo userInfo = user.getUserInfo();
            return UserDetailsDto.builder()
                    .firstname(userInfo.getFirstname())
                    .surname(userInfo.getSurname())
                    .age(userInfo.getAge())
                    .gender(userInfo.getGender().name())
                    .build();
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

}
