package pl.remplewicz.crowding.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.remplewicz.crowding.dto.UserAccountDto;
import pl.remplewicz.crowding.exception.UserAccountException;
import pl.remplewicz.crowding.service.IUserAccountService;
import pl.remplewicz.crowding.util.converter.UserAccountConverter;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserAccountController {

    IUserAccountService userService;

    @Autowired
    public UserAccountController(IUserAccountService userService) {
        this.userService = userService;
    }

    @GetMapping("all")
    public List<UserAccountDto> getAll() {
        return UserAccountConverter.entityListToDtoList(
                userService.getAll()
        );
    }

    @GetMapping("{userId}")
    public UserAccountDto get(@PathVariable("userId") long userId) throws UserAccountException.UserNotFoundAccountException {
        return UserAccountConverter.entityToDto(
                userService.get(userId)
        );
    }

    @PostMapping("register")
    public UserAccountDto register(@RequestBody UserAccountDto userAccount){
        return UserAccountConverter.entityToDto(
                userService.registerUser(
                        UserAccountConverter.createEntityFromDto(userAccount)
                )
        );
    }
}
