package pl.remplewicz.crowding.dto;/*
 * Copyright (c) 2022.
 * All Rights Reserved.
 * Created by:
 * Name: Arkadiusz Remplewicz
 * Index Number: 224413
 * E-mail: arkadiusz.remplewicz@gmail.com
 * Git-Hub Username: rempek99
 */


import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;
import java.util.Set;

@Getter
@Builder
public class CrowdingEventDetailsDto {

    private Long id;
    private String title;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime eventDate;
    private String description;
    private Integer slots;
    private Double latitude;
    private Double longitude;
    private String locationName;
    private UserDetailsDto organizer;
    private Set<UserDetailsDto> participants;
    private Integer version;
}
