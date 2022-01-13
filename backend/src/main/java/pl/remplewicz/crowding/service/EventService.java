package pl.remplewicz.crowding.service;/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * Created by:
 * Name: Arkadiusz Remplewicz
 * Index Number: 224413
 * E-mail: arkadiusz.remplewicz@gmail.com
 * Git-Hub Username: rempek99
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import pl.remplewicz.crowding.exception.DuplicationException;
import pl.remplewicz.crowding.exception.EventException;
import pl.remplewicz.crowding.exception.NotFoundException;
import pl.remplewicz.crowding.model.CrowdingEvent;
import pl.remplewicz.crowding.model.EventDistance;
import pl.remplewicz.crowding.model.EventLocation;
import pl.remplewicz.crowding.model.User;
import pl.remplewicz.crowding.repository.CrowdingEventRepo;
import pl.remplewicz.crowding.repository.UserRepo;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.*;

@Service
@Transactional(Transactional.TxType.REQUIRES_NEW)
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
    public CrowdingEvent get(Long id) {
        return eventRepository.findById(id).orElseThrow(() -> NotFoundException.eventNotFound(id));
    }

    @Override
    public CrowdingEvent signInToEvent(Long id, Principal participant) throws Exception {
        User user =
                userRepo.findByUsername(participant.getName()).orElseThrow(
                        () -> NotFoundException.createUsernameNotFound(participant.getName())
                );
        synchronized (this) {
            CrowdingEvent event = eventRepository.findById(id).orElseThrow(() -> NotFoundException.eventNotFound(id));
            if (event.getParticipants().contains(user)) {
                throw EventException.createAlreadySigned();
            }
            if (event.getOrganizer().equals(user)) {
                throw EventException.createOrganizerAsParticipantException();
            }
            if (event.getParticipants().size() >= event.getSlots()) {
                throw EventException.createNoSlotsException();
            }
            event.getParticipants().add(user);
            return eventRepository.saveAndFlush(event);
        }
    }

    @Override
    public CrowdingEvent signOutFromEvent(Long id, Principal principal) throws Exception {
        User user =
                userRepo.findByUsername(principal.getName()).orElseThrow(
                        () -> NotFoundException.createUsernameNotFound(principal.getName())
                );
        synchronized (this) {
            CrowdingEvent event = eventRepository.findById(id).orElseThrow(() -> NotFoundException.eventNotFound(id));
            if (!event.getParticipants().contains(user)) {
                throw EventException.createYouAreNotParticipantException();
            }
            event.getParticipants().remove(user);
            return eventRepository.saveAndFlush(event);
        }
    }

    @Override
    public List<EventDistance> getAllNear(EventLocation location) {
        return sortByLocation(getAll(),location);
    }

    private List<EventDistance> sortByLocation(List<CrowdingEvent> all, EventLocation location) {

        List <EventDistance> eventDistanceList = new LinkedList<>();
        all.forEach(event -> {
            Double distance = calculateDistance(event.getLocation().getLatitude(), location.getLatitude(),
                    event.getLocation().getLongitude(), location.getLongitude());
           eventDistanceList.add(new EventDistance(event,distance));
        });
        eventDistanceList.sort(Comparator.comparing(EventDistance::getDistance));

        return eventDistanceList;
    }

    private Double calculateDistance(Double x1, Double x2, Double y1, Double y2){
        return Math.sqrt( Math.pow(x2-x1,2) + Math.pow(y2-y1,2));
    }

    @Override
    public List<CrowdingEvent> getAll() {
        return eventRepository.findAll();
    }


}
