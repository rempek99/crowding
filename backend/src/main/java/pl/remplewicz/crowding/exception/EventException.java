package pl.remplewicz.crowding.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
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

public class EventException extends ResponseStatusException {

    private static final String NO_SLOTS = "No more slots free";
    private static final String ORGANIZER_AS_PARTICIPANT = "Event organizer can not be the participant";
    private static final String ALREADY_SIGNED = "You are already signed in event";
    private static final String YOU_ARE_NOT_PARTICIPANT = "You are are not participant of the event";

    public static EventNoSlotsException createNoSlotsException() {
        return new EventNoSlotsException(NO_SLOTS);
    }


    private EventException(HttpStatus status, @Nullable String reason) {
        super(status, reason);
    }

    public static EventOrganizerAsParticipantException createOrganizerAsParticipantException() {
        return new EventOrganizerAsParticipantException(ORGANIZER_AS_PARTICIPANT);
    }

    public static Exception createAlreadySigned() {
        return new EventAlreadySignedException(ALREADY_SIGNED);
    }

    public static Exception createYouAreNotParticipantException() {
        return new EventYouAreNotParticipantException(YOU_ARE_NOT_PARTICIPANT);
    }

    public static class EventAlreadySignedException extends EventException {

        public EventAlreadySignedException(String message) {
            super(HttpStatus.CONFLICT, message);
        }
    }

    public static class EventNoSlotsException extends EventException {

        public EventNoSlotsException(String message) {
            super(HttpStatus.CONFLICT, message);
        }
    }

    public static class EventOrganizerAsParticipantException extends EventException {

        public EventOrganizerAsParticipantException(String message) {
            super(HttpStatus.CONFLICT, message);
        }
    }

    public static class EventYouAreNotParticipantException extends EventException {

        public EventYouAreNotParticipantException(String message) {
            super(HttpStatus.CONFLICT, message);
        }
    }
}
