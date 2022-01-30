package pl.remplewicz.crowding.util.converter;/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * Created by:
 * Name: Arkadiusz Remplewicz
 * Index Number: 224413
 * E-mail: arkadiusz.remplewicz@gmail.com
 * Git-Hub Username: rempek99
 */

import pl.remplewicz.crowding.dto.CrowdingEventDetailsDto;
import pl.remplewicz.crowding.dto.CrowdingEventDto;
import pl.remplewicz.crowding.dto.CrowdingEventWithDistanceDto;
import pl.remplewicz.crowding.model.CrowdingEvent;
import pl.remplewicz.crowding.model.EventDistance;
import pl.remplewicz.crowding.model.EventLocation;
import pl.remplewicz.crowding.service.EventService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CrowdingEventConverter {

    private CrowdingEventConverter() {
    }

    public static CrowdingEventDto toDto(CrowdingEvent crowdingEvent) {
        return CrowdingEventDto.builder()
                .id(crowdingEvent.getId())
                .title(crowdingEvent.getTitle())
                .description(crowdingEvent.getDescription())
                .eventDate(crowdingEvent.getEventDate())
                .slots(crowdingEvent.getSlots())
                .participants(crowdingEvent.getParticipants().size())
                .longitude(crowdingEvent.getLocation().getLongitude())
                .latitude(crowdingEvent.getLocation().getLatitude())
                .locationName(crowdingEvent.getLocation().getName())
                .version(crowdingEvent.getVersion())
                .build();
    }

    public static List<CrowdingEventDto> toDtoList(List<CrowdingEvent> crowdingEvents) {
        return crowdingEvents.stream()
                .map(CrowdingEventConverter::toDto)
                .collect(Collectors.toList());
    }

    public static CrowdingEvent createEntityFromDto(CrowdingEventDto eventDto) {
        return new CrowdingEvent(null, eventDto.getEventDate(), eventDto.getTitle(), eventDto.getDescription(),
                eventDto.getSlots(), new EventLocation(null, eventDto.getLatitude(), eventDto.getLongitude(), eventDto.getLocationName()),
                null, Collections.emptySet(),eventDto.getVersion());
    }

    public static CrowdingEventDetailsDto toDtoWithDetails(CrowdingEvent crowdingEvent) {
        return CrowdingEventDetailsDto.builder()
                .id(crowdingEvent.getId())
                .title(crowdingEvent.getTitle())
                .description(crowdingEvent.getDescription())
                .eventDate(crowdingEvent.getEventDate())
                .slots(crowdingEvent.getSlots())
                .participants(UserConverter.toSetDtoWithDetails(crowdingEvent.getParticipants()))
                .latitude(crowdingEvent.getLocation().getLatitude())
                .longitude(crowdingEvent.getLocation().getLongitude())
                .locationName(crowdingEvent.getLocation().getName())
                .organizer(UserConverter.toDtoWithDetails(crowdingEvent.getOrganizer()))
                .version(crowdingEvent.getVersion())
                .build();

    }

    public static CrowdingEventWithDistanceDto toDtoWithDistance(EventDistance eventDistance) {
        CrowdingEvent crowdingEvent = eventDistance.getEvent();
        return CrowdingEventWithDistanceDto.builder()
                .id(crowdingEvent.getId())
                .title(crowdingEvent.getTitle())
                .description(crowdingEvent.getDescription())
                .eventDate(crowdingEvent.getEventDate())
                .slots(crowdingEvent.getSlots())
                .participants(crowdingEvent.getParticipants().size())
                .longitude(crowdingEvent.getLocation().getLongitude())
                .latitude(crowdingEvent.getLocation().getLatitude())
                .locationName(crowdingEvent.getLocation().getName())
                .distance(eventDistance.getDistance())
                .version(crowdingEvent.getVersion())
                .build();
    }

    public static CrowdingEventWithDistanceDto toDtoWithoutDistance(CrowdingEvent event) {
        return CrowdingEventWithDistanceDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .slots(event.getSlots())
                .participants(event.getParticipants().size())
                .longitude(event.getLocation().getLongitude())
                .latitude(event.getLocation().getLatitude())
                .locationName(event.getLocation().getName())
                .distance(null)
                .version(event.getVersion())
                .build();
    }

    public static List<CrowdingEventWithDistanceDto> toDtoWithDistanceList(List<EventDistance> eventDistanceList) {
        return eventDistanceList.stream().map(CrowdingEventConverter::toDtoWithDistance).collect(Collectors.toList());
    }

    public static List<CrowdingEventWithDistanceDto> toDtoWithoutDistanceList(List<CrowdingEvent> eventList) {
        return eventList.stream().map(CrowdingEventConverter::toDtoWithoutDistance).collect(Collectors.toList());
    }
}
