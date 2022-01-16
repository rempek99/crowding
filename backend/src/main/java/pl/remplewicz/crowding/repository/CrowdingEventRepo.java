package pl.remplewicz.crowding.repository;/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * Created by:
 * Name: Arkadiusz Remplewicz
 * Index Number: 224413
 * E-mail: arkadiusz.remplewicz@gmail.com
 * Git-Hub Username: rempek99
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import pl.remplewicz.crowding.model.CrowdingEvent;

import javax.persistence.LockModeType;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CrowdingEventRepo extends JpaRepository<CrowdingEvent, Long> {

    List<CrowdingEvent> findAllByEventDateIsAfter(ZonedDateTime date);

    @Lock(LockModeType.OPTIMISTIC)
    CrowdingEvent getById(Long id);

    Optional<CrowdingEvent> findById(Long id);

}
