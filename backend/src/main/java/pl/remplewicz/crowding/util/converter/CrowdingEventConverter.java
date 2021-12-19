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

import java.util.List;
import java.util.stream.Collectors;

public class CrowdingEventConverter {

    private CrowdingEventConverter() {
    }

    public static CrowdingEventDto toDto(CrowdingEvent crowdingEvent) {
        return CrowdingEventDto.builder()
                .title(crowdingEvent.getTitle())
                .description(crowdingEvent.getDescription())
//                .eventDate(crowdingEvent.getEventDate())
                .participants(crowdingEvent.getParticipants().size())
                .slots(crowdingEvent.getSlots())
                .longitude(crowdingEvent.getLocation().getLongitude())
                .latitude(crowdingEvent.getLocation().getLatitude())
                .build();
    }

    public static List<CrowdingEventDto> toDtoList(List<CrowdingEvent> crowdingEvents) {
        return crowdingEvents.stream()
                .map(CrowdingEventConverter::toDto)
                .collect(Collectors.toList());
    }
}
