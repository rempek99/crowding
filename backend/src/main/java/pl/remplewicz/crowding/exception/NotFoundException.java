package pl.remplewicz.crowding.exception;

/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * Created by:
 * Name: Arkadiusz Remplewicz
 * Index Number: 224413
 * E-mail: arkadiusz.remplewicz@gmail.com
 * Git-Hub Username: rempek99
 */

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends ResponseStatusException {

    private static final String USERNAME_NOT_FOUND_MESSAGE = "Username: '%s' not found";
    private static final String ID_NOT_FOUND_MESSAGE = "User with id: '%s' not found";

    private NotFoundException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public static NotFoundException createIdNotFound(Long id) {
        return new NotFoundException(HttpStatus.NOT_FOUND, String.format(ID_NOT_FOUND_MESSAGE, id));
    }


    public static NotFoundException createUsernameNotFound(String username) {
        return new NotFoundException(HttpStatus.NOT_FOUND, String.format(USERNAME_NOT_FOUND_MESSAGE, username));
    }
//    public NotFoundException(String message) {
//        super(message);
//    }
//
//    public NotFoundException(Class<?> clazz, long id) {
//        super(String.format("Entity %s with id %d not found", clazz.getSimpleName(), id));
//    }
//
//    public NotFoundException(Class<?> clazz, String id) {
//        super(String.format("Entity %s with id %s not found", clazz.getSimpleName(), id));
//    }
//
//    public NotFoundException(Class<?> clazz, Long id) {
//        super(String.format("Entity %s with id %s not found", clazz.getSimpleName(), id.toString()));
//    }

}