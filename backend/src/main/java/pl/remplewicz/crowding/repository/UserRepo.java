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

@Repository
@CacheConfig(cacheNames = "users")
public interface UserRepo extends JpaRepository<User,Long> {

    @Cacheable
    Optional<User> findById(Long objectId);

    @Cacheable
    Optional<User> findByUsername(String username);

    @Cacheable
    default User getById(Long id) {
        Optional<User> optionalUser = findById(id);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException(User.class, id);
        }
        if (!optionalUser.get().isEnabled()) {
            throw new NotFoundException(User.class, id);
        }
        return optionalUser.get();
    }

    @CacheEvict(allEntries = true)
    <S extends User> List<S> saveAll(Iterable<S> entities);
}
