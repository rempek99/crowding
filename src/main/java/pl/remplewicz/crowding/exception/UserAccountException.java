package pl.remplewicz.crowding.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserAccountException extends Exception{

    private static final String USER_NOT_FOUND = "User does not exist";

    public static UserNotFoundAccountException createUserNotFoundException(){
        return new UserNotFoundAccountException(USER_NOT_FOUND);
    }


    private UserAccountException(String message) {
        super(message);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public static class UserNotFoundAccountException extends UserAccountException {

        public UserNotFoundAccountException(String message) {
            super(message);
        }
    }
}
