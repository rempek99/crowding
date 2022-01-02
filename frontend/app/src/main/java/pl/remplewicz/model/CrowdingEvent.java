package pl.remplewicz.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.SerializedName;

import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrowdingEvent {


    private String title;

    private ZonedDateTime eventDate;

    private String description;

    private Integer participants;

    private Integer slots;

    private Double latitude;

    private Double longitude;
}
