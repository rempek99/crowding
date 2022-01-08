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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.remplewicz.crowding.dto.UserDetailsDto;
import pl.remplewicz.crowding.dto.UserRolesDto;
import pl.remplewicz.crowding.exception.UserAccountException;
import pl.remplewicz.crowding.model.Role;
import pl.remplewicz.crowding.model.User;
import pl.remplewicz.crowding.model.UserInfo;
import pl.remplewicz.crowding.service.IUserService;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("details/{id}")
    @RolesAllowed(Role.ADMIN)
    public UserDetailsDto getUserDetails(Principal principal, @PathVariable Long id) {
        User user = userService.findById(id);
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

    @GetMapping("details")
    @RolesAllowed(Role.USER)
    public UserDetailsDto getUserDetails(Principal principal) throws UserAccountException.UserNotFoundAccountException {
        User caller = userService.findByUsername(principal.getName());
        if(caller == null) {
            throw UserAccountException.createUserNotFoundException();
        }
            UserInfo userInfo = caller.getUserInfo();
            return UserDetailsDto.builder()
                    .firstname(userInfo.getFirstname())
                    .surname(userInfo.getSurname())
                    .age(userInfo.getAge())
                    .gender(userInfo.getGender().name())
                    .build();
    }

    @PutMapping("roles/activate/{id}/{role}")
    @RolesAllowed("ADMIN")
    public UserRolesDto activateRole(@PathVariable String role, @PathVariable Long id) {
        Set<Role> roles = userService.activateUserRole(id, role);
        return UserRolesDto.builder()
                .username(userService.findById(id).getUsername())
                .roles(roles.stream().map(Role::getAuthority).collect(Collectors.toList()))
                .build();
    }

    @PutMapping("roles/deactivate/{id}/{role}")
    @RolesAllowed("ADMIN")
    public UserRolesDto deactivateRole(@PathVariable String role, @PathVariable Long id) {
        Set<Role> roles = userService.deactivateUserRole(id, role);
        return UserRolesDto.builder()
                .username(userService.findById(id).getUsername())
                .roles(roles.stream().map(Role::getAuthority).collect(Collectors.toList()))
                .build();
    }

}
