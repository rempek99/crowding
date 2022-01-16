package pl.remplewicz.crowding.util;/*
 * Copyright (c) 2022.
 * All Rights Reserved.
 * Created by:
 * Name: Arkadiusz Remplewicz
 * Index Number: 224413
 * E-mail: arkadiusz.remplewicz@gmail.com
 * Git-Hub Username: rempek99
 */



public interface RetryingJob<T> {

    T runJob() throws Exception;
}
