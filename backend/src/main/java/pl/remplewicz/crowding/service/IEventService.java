package pl.remplewicz.crowding.service;/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * Created by:
 * Name: Arkadiusz Remplewicz
 * Index Number: 224413
 * E-mail: arkadiusz.remplewicz@gmail.com
 * Git-Hub Username: rempek99
 */

import pl.remplewicz.crowding.model.CrowdingEvent;

import java.util.List;

public interface IEventService {


    List<CrowdingEvent> getAll();
}
