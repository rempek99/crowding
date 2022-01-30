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
import org.springframework.web.bind.annotation.*;
import pl.remplewicz.crowding.dto.UserDetailsDto;
import pl.remplewicz.crowding.dto.UserRolesDto;
import pl.remplewicz.crowding.exception.UserAccountException;
import pl.remplewicz.crowding.model.Role;
import pl.remplewicz.crowding.model.User;
import pl.remplewicz.crowding.service.IUserService;
import pl.remplewicz.crowding.util.converter.UserConverter;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.List;
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

    @GetMapping("all")
    @RolesAllowed(Role.ADMIN)
    public List<UserRolesDto> getAllUsersWithRoles() {
        return UserConverter.toDtoWithRolesList(userService.getAll());
    }

    @GetMapping("details/{username}")
    @RolesAllowed({Role.USER, Role.ADMIN})
    public UserDetailsDto getUserDetails(@PathVariable String username) throws UserAccountException.UserNotFoundAccountException {
        User user = userService.findByUsername(username);
        return UserConverter.toDtoWithDetails(user);
    }

    @GetMapping("details")
    @RolesAllowed({Role.USER, Role.ADMIN})
    public UserDetailsDto getUserDetails(Principal principal) throws UserAccountException.UserNotFoundAccountException {
        User caller = userService.findByUsername(principal.getName());
        if (caller == null) {
            throw UserAccountException.createUserNotFoundException();
        }
        if (caller.getUserInfo() == null) {
            return UserDetailsDto.builder().username(caller.getUsername()).build();
        }
        return UserConverter.toDtoWithDetails(caller);
    }

    @PutMapping("details/set")
    @RolesAllowed({Role.USER, Role.ADMIN})
    public UserDetailsDto setUserDetails(Principal principal, @RequestBody UserDetailsDto newDetails) throws UserAccountException.UserNotFoundAccountException {
        User caller = userService.findByUsername(principal.getName());
        if (caller == null) {
            throw UserAccountException.createUserNotFoundException();
        }
        return UserConverter.toDtoWithDetails(
                userService.setUserDetails(caller, UserConverter.createUserInfoFromDto(newDetails, caller))
        );
    }

    @PutMapping("enable/{id}/{active}")
    @RolesAllowed(Role.ADMIN)
    public UserRolesDto changeUserActive(@PathVariable Long id, @PathVariable Boolean active, Principal principal) throws UserAccountException.UserForbiddenEditException {
        return UserConverter.toDtoWithRoles(userService.changeUserEnabled(id, active, principal));
    }

    @PutMapping("roles/activate/{id}/{role}")
    @RolesAllowed(Role.ADMIN)
    public UserRolesDto activateRole(@PathVariable String role, @PathVariable Long id, Principal principal) throws UserAccountException.UserForbiddenEditException {
        User user = userService.activateUserRole(id, role, principal);
        return UserConverter.toDtoWithRoles(user);
    }

    @PutMapping("roles/deactivate/{id}/{role}")
    @RolesAllowed(Role.ADMIN)
    public UserRolesDto deactivateRole(@PathVariable String role, @PathVariable Long id, Principal principal) throws UserAccountException.UserForbiddenEditException {
        User user = userService.deactivateUserRole(id, role, principal);
        return UserConverter.toDtoWithRoles(user);
    }

}
