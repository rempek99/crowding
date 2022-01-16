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
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(Transactional.TxType.REQUIRES_NEW)
public class EventService implements IEventService {

    // 1° is 111,1km in real
    private static final double CONSTANT = 111.1;
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
        if (event.getEventDate().isBefore(ZonedDateTime.now().plus(1, ChronoUnit.HOURS))) {
            throw EventException.createEventTooEarlyException();
        }
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
            if (event.getEventDate().isBefore(ZonedDateTime.now())) {
                throw EventException.createEventEnded();
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
            if (event.getEventDate().isBefore(ZonedDateTime.now())) {
                throw EventException.createEventEnded();
            }
            event.getParticipants().remove(user);
            return eventRepository.saveAndFlush(event);
        }
    }

    @Override
    public List<CrowdingEvent> getAllFutureEvents() {
        return eventRepository.findAllByEventDateIsAfter(ZonedDateTime.now());
    }

    @Override
    public List<EventDistance> getAllNear(EventLocation location) {
        return sortByLocation(getAllFutureEvents(), location);
    }

    @Override
    public List<EventDistance> getAllNearUserEvents(EventLocation location, Principal principal) {
        User user =
                userRepo.findByUsername(principal.getName()).orElseThrow(() -> NotFoundException.createUsernameNotFound(principal.getName()));
        List<EventDistance> eventDistanceList = sortByLocation(getAll(), location);
        return eventDistanceList.stream()
                .filter(
                        event -> event.getEvent().getOrganizer().equals(user)
                                || event.getEvent().getParticipants()
                                .stream().anyMatch(participant -> participant.equals(user)))
                .collect(Collectors.toList());
    }

    @Override
    public List<CrowdingEvent> getAllUserEvents(Principal principal) {
        User user =
                userRepo.findByUsername(principal.getName()).orElseThrow(() -> NotFoundException.createUsernameNotFound(principal.getName()));
        List<CrowdingEvent> eventList = getAll();
        return eventList.stream()
                .filter(
                        event -> event.getOrganizer().equals(user)
                                || event.getParticipants()
                                .stream().anyMatch(participant -> participant.equals(user)))
                .collect(Collectors.toList());
    }

    private List<EventDistance> sortByLocation(List<CrowdingEvent> all, EventLocation location) {

        List<EventDistance> eventDistanceList = new LinkedList<>();
        all.forEach(event -> {
            Double distance = calculateDistance(event.getLocation().getLatitude(), location.getLatitude(),
                    event.getLocation().getLongitude(), location.getLongitude());
            eventDistanceList.add(new EventDistance(event, distance));
        });
        eventDistanceList.sort(Comparator.comparing(EventDistance::getDistance));

        return eventDistanceList;
    }

    private Double calculateDistance(Double x1, Double x2, Double y1, Double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)) * CONSTANT;
    }

    @Override
    public List<CrowdingEvent> getAll() {
        return eventRepository.findAll();
    }


}
