package pl.remplewicz.crowding.service;

import pl.remplewicz.crowding.exception.DuplicationException;
import pl.remplewicz.crowding.exception.UserAccountException;
import pl.remplewicz.crowding.model.Role;
import pl.remplewicz.crowding.model.User;
import pl.remplewicz.crowding.model.UserInfo;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * Created by:
 * Name: Arkadiusz Remplewicz
 * Index Number: 224413
 * E-mail: arkadiusz.remplewicz@gmail.com
 * Git-Hub Username: rempek99
 */

public interface IUserService {
    User findById(Long id);

    User findByUsername(String username);

    User register(User user) throws DuplicationException;

    User changeUserEnabled(Long id, Boolean enabled, Principal principal) throws UserAccountException.UserForbiddenEditException;

    User activateUserRole(Long id, String roleName, Principal principal) throws UserAccountException.UserForbiddenEditException;

    User deactivateUserRole(Long id, String roleName,Principal principal) throws UserAccountException.UserForbiddenEditException;

    //todo delete it later
    User test(String login);

    User setUserDetails(User caller, UserInfo userInfoFromDto);

    List<User> getAll();

    Optional<User> getPrincipalByUsername(String username);
}
