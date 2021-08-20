package pl.remplewicz.crowding.service;

import org.springframework.stereotype.Service;
import pl.remplewicz.crowding.exception.UserAccountException;
import pl.remplewicz.crowding.model.UserAccount;

import java.util.List;

public interface IUserAccountService {

    List<UserAccount> getAll();

    UserAccount get(Long id) throws UserAccountException.UserNotFoundAccountException;

    UserAccount registerUser(UserAccount userAccount);

    UserAccount deactivateUser(Long userId) throws UserAccountException.UserNotFoundAccountException;

    UserAccount activateUser(Long userId) throws UserAccountException.UserNotFoundAccountException;

}
