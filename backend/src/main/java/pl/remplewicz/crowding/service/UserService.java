package pl.remplewicz.crowding.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.remplewicz.crowding.dto.UserRolesDto;
import pl.remplewicz.crowding.exception.DuplicationException;
import pl.remplewicz.crowding.exception.NotFoundException;
import pl.remplewicz.crowding.model.Role;
import pl.remplewicz.crowding.model.User;
import pl.remplewicz.crowding.repository.UserRepo;

import java.util.Optional;
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

@Service
public class UserService implements IUserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> NotFoundException.createIdNotFound(id));
    }

    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(() -> NotFoundException.createUsernameNotFound(username));
    }

    @Override
    public User register(User user) throws DuplicationException {
        //encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            userRepo.saveAndFlush(user);
        } catch (DataIntegrityViolationException ex) {
            if (checkUsernameDuplicated(user.getUsername())) {
                throw DuplicationException.createUsernameTakenException(user.getUsername());
            }
            throw ex;
        }
        user.addAuthority(Role.ADMIN,false);
        user.addAuthority(Role.USER);
        return userRepo.save(user);
    }

    private boolean checkUsernameDuplicated(String username) {
        return userRepo.findAll().stream().anyMatch(user -> user.getUsername().equals(username));
    }

    @Override
    public User test(String login) {
        User tester = new User(login, passwordEncoder.encode("1234"));
        userRepo.saveAndFlush(tester);
        tester.addAuthority(Role.ADMIN);
        return userRepo.save(tester);
    }

    @Override
    public Set<Role> activateUserRole(Long id, String roleName) {
        User user = findById(id);
        user.addAuthority(roleName);
        return userRepo.save(user).getAuthorities().stream().filter(Role::isEnabled).collect(Collectors.toSet());
    }

    @Override
    public Set<Role> deactivateUserRole(Long id, String roleName) {
        User user = findById(id);
        user.removeAuthority(roleName);
        return userRepo.save(user).getAuthorities().stream().filter(Role::isEnabled).collect(Collectors.toSet());
    }
}
