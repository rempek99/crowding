package pl.remplewicz.crowding.service;

import pl.remplewicz.crowding.exception.DuplicationException;
import pl.remplewicz.crowding.exception.UserAccountException;
import pl.remplewicz.crowding.model.User;
import pl.remplewicz.crowding.model.UserInfo;

import java.security.Principal;
import java.util.List;

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

    User setUserDetails(User caller, UserInfo userInfoFromDto);

    List<User> getAll();

}
