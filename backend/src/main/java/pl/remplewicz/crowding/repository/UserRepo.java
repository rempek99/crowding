package pl.remplewicz.crowding.repository;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.remplewicz.crowding.exception.NotFoundException;
import pl.remplewicz.crowding.model.User;

import java.util.List;
import java.util.Optional;

/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * Created by:
 * Name: Arkadiusz Remplewicz
 * Index Number: 224413
 * E-mail: arkadiusz.remplewicz@gmail.com
 * Git-Hub Username: rempek99
 */

@Repository
@CacheConfig(cacheNames = "users")
public interface UserRepo extends JpaRepository<User,Long> {

    @Cacheable
    Optional<User> findById(Long objectId);

    @Cacheable
    Optional<User> findByUsername(String username);

    @CacheEvict(allEntries = true)
    <S extends User> List<S> saveAll(Iterable<S> entities);
}
