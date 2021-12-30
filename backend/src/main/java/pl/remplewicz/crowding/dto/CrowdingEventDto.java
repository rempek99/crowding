package pl.remplewicz.crowding.dto;/*
 * Copyright (c) 2021.
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
import org.springframework.lang.Nullable;

import java.time.ZonedDateTime;

@Getter
@Builder
public class CrowdingEventDto {

    private String title;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime eventDate;
    private String description;
    @Nullable
    private int participants;
    private int slots;
    private double latitude;
    private double longitude;
}
