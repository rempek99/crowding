package pl.remplewicz.crowding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.remplewicz.crowding.model.UserAccount;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount,Long> {
}
