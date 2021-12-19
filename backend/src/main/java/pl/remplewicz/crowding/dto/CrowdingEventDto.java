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
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CrowdingEventDto {

    private String title;
//    private LocalDateTime eventDate;
    private String description;
    private int participants;
    private int slots;
    private double latitude;
    private double longitude;
}
