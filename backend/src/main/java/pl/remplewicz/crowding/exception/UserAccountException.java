package pl.remplewicz.crowding.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * Created by:
 * Name: Arkadiusz Remplewicz
 * Index Number: 224413
 * E-mail: arkadiusz.remplewicz@gmail.com
 * Git-Hub Username: rempek99
 */

public class UserAccountException extends Exception{

    private static final String USER_NOT_FOUND = "User does not exist";
    private static final String FORBIDDEN_EDIT = "That type of edit is forbidden";

    public static UserNotFoundAccountException createUserNotFoundException(){
        return new UserNotFoundAccountException(USER_NOT_FOUND);
    }


    private UserAccountException(String message) {
        super(message);
    }

    public static UserForbiddenEditException createForbiddenEditException() {
        return new UserForbiddenEditException(FORBIDDEN_EDIT);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public static class UserNotFoundAccountException extends UserAccountException {

        public UserNotFoundAccountException(String message) {
            super(message);
        }
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public static class UserForbiddenEditException extends UserAccountException {

        public UserForbiddenEditException(String message) {
            super(message);
        }
    }
}
