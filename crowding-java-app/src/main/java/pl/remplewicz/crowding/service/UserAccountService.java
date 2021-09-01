package pl.remplewicz.crowding.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.remplewicz.crowding.exception.UserAccountException;
import pl.remplewicz.crowding.model.UserAccount;
import pl.remplewicz.crowding.repository.UserAccountRepository;

import java.util.List;

@Service
public class UserAccountService implements IUserAccountService {

    private final UserAccountRepository userAccountRepository;

    @Autowired
    public UserAccountService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    public List<UserAccount> getAll(){
        return userAccountRepository.findAll();
    }

    @Override
    public UserAccount get(Long id) throws UserAccountException.UserNotFoundAccountException {
        return userAccountRepository.findById(id).orElseThrow(UserAccountException::createUserNotFoundException);
    }

    @Override
    public UserAccount registerUser(UserAccount userAccount) {
            return userAccountRepository.save(userAccount);
    }

    @Override
    public UserAccount deactivateUser(Long userId) throws UserAccountException.UserNotFoundAccountException {
        UserAccount user = userAccountRepository.findById(userId).orElseThrow(UserAccountException::createUserNotFoundException);
        user.setActive(false);
        return userAccountRepository.save(user);
    }

    @Override
    public UserAccount activateUser(Long userId) throws UserAccountException.UserNotFoundAccountException {
        UserAccount user = userAccountRepository.findById(userId).orElseThrow(UserAccountException::createUserNotFoundException);
        user.setActive(true);
        return userAccountRepository.save(user);
    }
}