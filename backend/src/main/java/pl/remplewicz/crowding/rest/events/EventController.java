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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.remplewicz.crowding.dto.CrowdingEventDto;
import pl.remplewicz.crowding.model.CrowdingEvent;
import pl.remplewicz.crowding.model.Role;
import pl.remplewicz.crowding.service.IEventService;
import pl.remplewicz.crowding.util.converter.CrowdingEventConverter;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.List;

@RestController
public class EventController {

    private final IEventService eventService;

    @Autowired
    public EventController(IEventService eventService) {
        this.eventService = eventService;
    }

    @PermitAll
    @GetMapping("api/public/events")
    public List<CrowdingEventDto> getAllEvents() {
        return CrowdingEventConverter.toDtoList(eventService.getAll());
    }

    @RolesAllowed(Role.USER)
    @PostMapping("api/events/create")
    public CrowdingEventDto createEvent(CrowdingEventDto eventDto, Principal principal) throws Exception {
        CrowdingEvent event = CrowdingEventConverter.createEntityFromDto(eventDto);
        return CrowdingEventConverter.toDto(eventService.create(event, principal));
    }
}
