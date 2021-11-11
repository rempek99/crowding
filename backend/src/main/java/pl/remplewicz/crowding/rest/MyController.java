package pl.remplewicz.crowding.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.remplewicz.crowding.dto.UserDto;
import pl.remplewicz.crowding.model.Role;
import pl.remplewicz.crowding.service.UserService;
import pl.remplewicz.crowding.util.converter.UserConverter;

import javax.annotation.security.RolesAllowed;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api")
public class MyController {

    @GetMapping
    public Map<String, Object> home() {
        Map<String,Object> model = new HashMap<>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "Hello World");
        return model;
    }
    @GetMapping("user")
    public String helloUser() {
        return "Hello User!";
    }

    @GetMapping("admin")
    @RolesAllowed(Role.ADMIN)
    public String helloAdmin() {
        return "Hello Admin!";
    }

    @Autowired
    UserService userService;

    @PostMapping("public/test")
    public UserDto test(@RequestBody String login){
        return UserConverter.toDto(userService.test(login));
    }
}
