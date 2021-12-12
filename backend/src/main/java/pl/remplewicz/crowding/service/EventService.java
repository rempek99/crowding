package pl.remplewicz.crowding.service;/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * Created by:
 * Name: Arkadiusz Remplewicz
 * Index Number: 224413
 * E-mail: arkadiusz.remplewicz@gmail.com
 * Git-Hub Username: rempek99
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.remplewicz.crowding.model.CrowdingEvent;
import pl.remplewicz.crowding.repository.CrowdingEventRepo;

import java.util.List;

@Service
public class EventService implements IEventService{

    private final CrowdingEventRepo eventRepository;

    @Autowired
    public EventService(CrowdingEventRepo eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<CrowdingEvent> getAll() {
        return eventRepository.findAll();
    }
}
