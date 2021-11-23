package pl.remplewicz.crowding.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * Created by:
 * Name: Arkadiusz Remplewicz
 * Index Number: 224413
 * E-mail: arkadiusz.remplewicz@gmail.com
 * Git-Hub Username: rempek99
 */

public class DuplicationException extends ResponseStatusException {

    private static final String USERNAME_TAKEN_MESSAGE = "Username: '%s' is already taken";

    private DuplicationException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public static DuplicationException createUsernameTakenException(String username){
        return new DuplicationException(HttpStatus.CONFLICT,String.format(USERNAME_TAKEN_MESSAGE,username));
    }
}
