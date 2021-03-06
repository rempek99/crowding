package pl.remplewicz.crowding.model;/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * Created by:
 * Name: Arkadiusz Remplewicz
 * Index Number: 224413
 * E-mail: arkadiusz.remplewicz@gmail.com
 * Git-Hub Username: rempek99
 */


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"title", "event_date","organizer_id"})
)
public class CrowdingEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_date")
    private ZonedDateTime eventDate;

    private String title;

    private String description;

    private int slots;

    @ManyToOne(cascade = CascadeType.ALL)
    private EventLocation location;

    @Setter
    @OneToOne
    private User organizer;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<User> participants = new HashSet<>();

    @Version
    private Integer version;
}
