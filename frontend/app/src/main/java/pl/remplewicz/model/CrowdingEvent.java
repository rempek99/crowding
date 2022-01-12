package pl.remplewicz.model;


import org.apache.commons.lang3.StringUtils;

import java.time.ZonedDateTime;
import java.util.Locale;

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

    private Long id;

    private String title;

    private ZonedDateTime eventDate;

    private String description;

    private Integer participants;

    private Integer slots;

    private Double latitude;

    private Double longitude;

    private Double distance;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtils.rightPad(title,25));
        sb.append(System.lineSeparator());
        sb.append(StringUtils.rightPad(participants.toString()+'/'+slots.toString(),10));
        if(distance!=null) {
            sb.append(StringUtils.rightPad("Distance:" + String.format(Locale.ROOT,"%.2f",distance), 15));
        }
        return sb.toString();
    }
}
