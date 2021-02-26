package com.example.datanor.controller;

import com.example.datanor.model.AppUser;
import com.example.datanor.model.AppUserDto;
import com.example.datanor.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("user")
    public List<AppUser> get() {
        return userService.getUsers();
    }

    @PostMapping("user")
    public AppUser create(@Valid @RequestBody AppUserDto user) {
        return userService.createUser(user);
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody AppUserDto user){
        return userService.loginUser(user);
    }
}
