package pl.remplewicz.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CrowdingEvent {
    private String title;
    private LocalDateTime eventDate;
    private String description;
    private int participants;
    private int slots;
    private double latitude;
    private double longitude;
}
