package pl.remplewicz.crowding.rest.events;/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * Created by:
 * Name: Arkadiusz Remplewicz
 * Index Number: 224413
 * E-mail: arkadiusz.remplewicz@gmail.com
 * Git-Hub Username: rempek99
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.*;
import pl.remplewicz.crowding.dto.CrowdingEventDetailsDto;
import pl.remplewicz.crowding.dto.CrowdingEventDto;
import pl.remplewicz.crowding.dto.CrowdingEventWithDistanceDto;
import pl.remplewicz.crowding.exception.OptimisticLockExceptionWrapper;
import pl.remplewicz.crowding.model.CrowdingEvent;
import pl.remplewicz.crowding.model.EventLocation;
import pl.remplewicz.crowding.model.Role;
import pl.remplewicz.crowding.service.IEventService;
import pl.remplewicz.crowding.util.RetryingJob;
import pl.remplewicz.crowding.util.converter.CrowdingEventConverter;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.List;

@RestController
public class EventController {

    private final IEventService eventService;
    private final static int RETRIES_COUNTER_LIMIT = 2;

    @Autowired
    public EventController(IEventService eventService) {
        this.eventService = eventService;
    }

    @PermitAll
    @GetMapping("api/public/events")
    public List<CrowdingEventDto> getAllEvents() {
        return CrowdingEventConverter.toDtoList(eventService.getAllFutureEvents());
    }

    @PermitAll
    @GetMapping("api/public/events/nearest/{latitude}/{longitude}")
    public List<CrowdingEventWithDistanceDto> getNearEvents(@PathVariable Double latitude,
                                                            @PathVariable Double longitude) {
        EventLocation location = new EventLocation(null, latitude, longitude,null);
        return CrowdingEventConverter.toDtoWithDistanceList(eventService.getAllNear(location));
    }

    @RolesAllowed({Role.USER, Role.ADMIN})
    @GetMapping("api/events/myevents/{latitude}/{longitude}")
    public List<CrowdingEventWithDistanceDto> getMyNearEvents(@PathVariable Double latitude,
                                                              @PathVariable Double longitude, Principal principal) {
        EventLocation location = new EventLocation(null, latitude, longitude,null);
        return CrowdingEventConverter.toDtoWithDistanceList(eventService.getAllNearUserEvents(location, principal));

    }

    @RolesAllowed({Role.USER, Role.ADMIN})
    @GetMapping("api/events/myevents")
    public List<CrowdingEventWithDistanceDto> getMyEvents(Principal principal) {
        return CrowdingEventConverter.toDtoWithoutDistanceList(eventService.getAllUserEvents(principal));
    }

    @RolesAllowed({Role.USER, Role.ADMIN})
    @GetMapping("api/events/{id}")
    public CrowdingEventDetailsDto getEvent(@PathVariable Long id) throws Exception {
        return CrowdingEventConverter.toDtoWithDetails(eventService.get(id));
    }

    @RolesAllowed({Role.USER, Role.ADMIN})
    @PostMapping("api/events/create")
    public CrowdingEventDto createEvent(@RequestBody CrowdingEventDto eventDto, Principal principal) throws Exception {
        CrowdingEvent event = CrowdingEventConverter.createEntityFromDto(eventDto);
        return CrowdingEventConverter.toDto(eventService.create(event, principal));
    }

    @RolesAllowed({Role.USER, Role.ADMIN})
    @PutMapping("api/events/sign/{id}")
    public CrowdingEventDetailsDto signInToEvent(@PathVariable Long id, Principal principal) throws Exception {
        CrowdingEvent jobResult = null;
        RetryingJob<CrowdingEvent> job = new RetryingJob<CrowdingEvent>() {
            @Override
            public CrowdingEvent runJob() throws Exception {
                return eventService.signInToEvent(id, principal);
            }
        };
        for (int i = 0; i <= RETRIES_COUNTER_LIMIT; i++) {
            try {
                jobResult = job.runJob();
                break;
            } catch (ObjectOptimisticLockingFailureException ex) {
                if (i == RETRIES_COUNTER_LIMIT) {
                    throw new OptimisticLockExceptionWrapper();
                }
            }
        }
        return CrowdingEventConverter.toDtoWithDetails(jobResult);
    }

    @RolesAllowed({Role.USER, Role.ADMIN})
    @PutMapping("api/events/signout/{id}")
    public CrowdingEventDetailsDto signOutFromEvent(@PathVariable Long id, Principal principal) throws Exception {
        CrowdingEvent jobResult = null;
        RetryingJob<CrowdingEvent> job = new RetryingJob<CrowdingEvent>() {
            @Override
            public CrowdingEvent runJob() throws Exception {
                return eventService.signOutFromEvent(id, principal);
            }
        };
        for (int i = 0; i <= RETRIES_COUNTER_LIMIT; i++) {
            try {
                jobResult = job.runJob();
                break;
            } catch (ObjectOptimisticLockingFailureException ex) {
                if (i == RETRIES_COUNTER_LIMIT) {
                    throw new OptimisticLockExceptionWrapper();
                }
            }
        }
        return CrowdingEventConverter.toDtoWithDetails(jobResult);
    }


}
