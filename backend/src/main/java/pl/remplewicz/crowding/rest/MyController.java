package pl.remplewicz.crowding.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.remplewicz.crowding.dto.UserDto;
import pl.remplewicz.crowding.model.Role;
import pl.remplewicz.crowding.service.IUserService;
import pl.remplewicz.crowding.util.converter.UserConverter;

import javax.annotation.security.RolesAllowed;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * Created by:
 * Name: Arkadiusz Remplewicz
 * Index Number: 224413
 * E-mail: arkadiusz.remplewicz@gmail.com
 * Git-Hub Username: rempek99
 */

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
    @GetMapping("userGreet")
    public String helloUser() {
        return "Hello User!";
    }

    @GetMapping("adminGreet")
    @RolesAllowed(Role.ADMIN)
    public String helloAdmin() {
        return "Hello Admin!";
    }

    @Autowired
    IUserService userService;
}
