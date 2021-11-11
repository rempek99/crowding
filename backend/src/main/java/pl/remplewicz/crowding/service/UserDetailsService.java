package pl.remplewicz.crowding.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.remplewicz.crowding.exception.UserAccountException;
import pl.remplewicz.crowding.model.User;

import java.util.List;

public interface UserDetailsService {

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
