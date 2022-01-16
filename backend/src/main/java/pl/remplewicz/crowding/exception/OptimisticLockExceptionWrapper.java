package pl.remplewicz.crowding.exception;/*
 * Copyright (c) 2022.
 * All Rights Reserved.
 * Created by:
 * Name: Arkadiusz Remplewicz
 * Index Number: 224413
 * E-mail: arkadiusz.remplewicz@gmail.com
 * Git-Hub Username: rempek99
 */

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ResponseStatusException;

public class OptimisticLockExceptionWrapper extends ResponseStatusException {

    private final static String OPTIMISTIC_EXCEPTION_MESSAGE = "Parallel change occurred, refresh and try again";


    public OptimisticLockExceptionWrapper() {
        super(HttpStatus.CONFLICT, OPTIMISTIC_EXCEPTION_MESSAGE);
    }
}
