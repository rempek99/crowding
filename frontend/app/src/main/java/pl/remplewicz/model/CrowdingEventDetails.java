package pl.remplewicz.model;


import java.time.ZonedDateTime;
import java.util.Set;

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
public class CrowdingEventDetails {

    private Long id;
    private String title;
    private ZonedDateTime eventDate;
    private String description;
    private Integer slots;
    private Double latitude;
    private Double longitude;
    private UserDetails organizer;
    private Set<UserDetails> participants;
    private Double distance;
    private Integer version;
}
