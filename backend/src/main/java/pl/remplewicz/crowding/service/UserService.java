package pl.remplewicz.crowding.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.remplewicz.crowding.exception.DuplicationException;
import pl.remplewicz.crowding.exception.NotFoundException;
import pl.remplewicz.crowding.model.Role;
import pl.remplewicz.crowding.model.User;
import pl.remplewicz.crowding.repository.UserRepo;

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
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public User findById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> NotFoundException.createIdNotFound(id));
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(() -> NotFoundException.createUsernameNotFound(username));
    }

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
        user.addAuthority(new Role(user, Role.ADMIN, false));
        user.addAuthority(new Role(user, Role.USER, true));
        return userRepo.save(user);
    }

    private boolean checkUsernameDuplicated(String username) {
        return userRepo.findAll().stream().anyMatch(user -> user.getUsername().equals(username));
    }

    public User test(String login) {
        User tester = new User(login, passwordEncoder.encode("1234"));
        userRepo.saveAndFlush(tester);
        tester.addAuthority(new Role(tester, Role.ADMIN, true));
        return userRepo.save(tester);
    }
}
