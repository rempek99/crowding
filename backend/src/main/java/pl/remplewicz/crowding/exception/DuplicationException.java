package pl.remplewicz.crowding.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DuplicationException extends ResponseStatusException {

    private static final String USERNAME_TAKEN_MESSAGE = "Username: '%s' is already taken";

    public DuplicationException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public static DuplicationException createUsernameTakenException(String username){
        return new DuplicationException(HttpStatus.CONFLICT,String.format(USERNAME_TAKEN_MESSAGE,username));
    }
}
