package pl.remplewicz.crowding.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
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

public class UserAccountException extends ResponseStatusException {

    private static final String USER_NOT_FOUND = "User does not exist";
    private static final String FORBIDDEN_EDIT = "That type of edit is forbidden";

    private UserAccountException(HttpStatus status, String message) {
        super(status,message);
    }

    public static UserNotFoundAccountException createUserNotFoundException(){
        return new UserNotFoundAccountException(USER_NOT_FOUND);
    }
    public static class UserNotFoundAccountException extends UserAccountException {

        public UserNotFoundAccountException(String message) {
            super(HttpStatus.NOT_FOUND, message);
        }

    }

    public static UserForbiddenEditException createForbiddenEditException() {
        return new UserForbiddenEditException(FORBIDDEN_EDIT);
    }

    public static class UserForbiddenEditException extends UserAccountException {

        public UserForbiddenEditException(String message) {
            super(HttpStatus.FORBIDDEN,message);
        }
    }
}
