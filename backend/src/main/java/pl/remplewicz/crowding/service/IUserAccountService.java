package pl.remplewicz.crowding.service;

import pl.remplewicz.crowding.exception.UserAccountException;
import pl.remplewicz.crowding.model.User;

import java.util.List;

public interface IUserAccountService {

    List<User> getAll();

    User get(Long id) throws UserAccountException.UserNotFoundAccountException;

    User registerUser(User user);

    User deactivateUser(Long userId) throws UserAccountException.UserNotFoundAccountException;

    User activateUser(Long userId) throws UserAccountException.UserNotFoundAccountException;

}
