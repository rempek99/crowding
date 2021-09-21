package pl.remplewicz.crowding.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.remplewicz.crowding.exception.UserAccountException;
import pl.remplewicz.crowding.model.User;
import pl.remplewicz.crowding.repository.UserAccountRepository;

import java.util.List;

@Service
public class UserAccountService implements IUserAccountService {

    private final UserAccountRepository userAccountRepository;

    @Autowired
    public UserAccountService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    public List<User> getAll(){
        return userAccountRepository.findAll();
    }

    @Override
    public User get(Long id) throws UserAccountException.UserNotFoundAccountException {
        return userAccountRepository.findById(id).orElseThrow(UserAccountException::createUserNotFoundException);
    }

    @Override
    public User registerUser(User user) {
            return userAccountRepository.save(user);
    }

    @Override
    public User deactivateUser(Long userId) throws UserAccountException.UserNotFoundAccountException {
        User user = userAccountRepository.findById(userId).orElseThrow(UserAccountException::createUserNotFoundException);
        user.setEnabled(false);
        return userAccountRepository.save(user);
    }

    @Override
    public User activateUser(Long userId) throws UserAccountException.UserNotFoundAccountException {
        User user = userAccountRepository.findById(userId).orElseThrow(UserAccountException::createUserNotFoundException);
        user.setEnabled(true);
        return userAccountRepository.save(user);
    }
}
