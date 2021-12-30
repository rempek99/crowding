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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import pl.remplewicz.crowding.exception.DuplicationException;
import pl.remplewicz.crowding.exception.NotFoundException;
import pl.remplewicz.crowding.model.CrowdingEvent;
import pl.remplewicz.crowding.model.User;
import pl.remplewicz.crowding.repository.CrowdingEventRepo;
import pl.remplewicz.crowding.repository.UserRepo;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class EventService implements IEventService {

    private final CrowdingEventRepo eventRepository;
    private final UserRepo userRepo;

    @Autowired
    public EventService(CrowdingEventRepo eventRepository, UserRepo userRepo) {
        this.eventRepository = eventRepository;
        this.userRepo = userRepo;
    }

    @Override
    public CrowdingEvent create(CrowdingEvent event, Principal creator) throws Exception {
        User user =
                userRepo.findByUsername(creator.getName()).orElseThrow(
                        () -> NotFoundException.createUsernameNotFound(creator.getName())
                );
        event.setOrganizer(user);
        try {
            return eventRepository.saveAndFlush(event);
        } catch (DataIntegrityViolationException ex) {
            throw DuplicationException.createEventAlreadyExistsException(event.getTitle());
        }
    }

    @Override
    public List<CrowdingEvent> getAll() {
        return eventRepository.findAll();
    }
}
