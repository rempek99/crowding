package pl.remplewicz.crowding.service;

import pl.remplewicz.crowding.exception.DuplicationException;
import pl.remplewicz.crowding.model.Role;
import pl.remplewicz.crowding.model.User;
import pl.remplewicz.crowding.model.UserInfo;

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

    Set<Role> activateUserRole(Long id, String roleName);

    Set<Role> deactivateUserRole(Long id, String roleName);

    //todo delete it later
    User test(String login);

    User setUserDetails(User caller, UserInfo userInfoFromDto);
}
