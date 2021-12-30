package pl.remplewicz.crowding.util.converter;/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * Created by:
 * Name: Arkadiusz Remplewicz
 * Index Number: 224413
 * E-mail: arkadiusz.remplewicz@gmail.com
 * Git-Hub Username: rempek99
 */

import pl.remplewicz.crowding.dto.CrowdingEventDto;
import pl.remplewicz.crowding.model.CrowdingEvent;
import pl.remplewicz.crowding.model.EventLocation;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CrowdingEventConverter {

    private CrowdingEventConverter() {
    }

    public static CrowdingEventDto toDto(CrowdingEvent crowdingEvent) {
        return CrowdingEventDto.builder()
                .title(crowdingEvent.getTitle())
                .description(crowdingEvent.getDescription())
                .eventDate(crowdingEvent.getEventDate())
                .slots(crowdingEvent.getSlots())
                .participants(crowdingEvent.getParticipants().size())
                .longitude(crowdingEvent.getLocation().getLongitude())
                .latitude(crowdingEvent.getLocation().getLatitude())
                .build();
    }

    public static List<CrowdingEventDto> toDtoList(List<CrowdingEvent> crowdingEvents) {
        return crowdingEvents.stream()
                .map(CrowdingEventConverter::toDto)
                .collect(Collectors.toList());
    }

    public static CrowdingEvent createEntityFromDto(CrowdingEventDto eventDto) {
        return new CrowdingEvent(null, eventDto.getEventDate(), eventDto.getTitle(), eventDto.getDescription(),
                eventDto.getSlots(), new EventLocation(null, eventDto.getLatitude(), eventDto.getLongitude()),
                null, Collections.emptySet());
    }
}
