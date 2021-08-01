package com.ingeneral.apidefender.rest.controller;

import com.ingeneral.apidefender.ApiDefender;
import com.ingeneral.apidefender.rest.response.UserResponse;
import com.ingeneral.apidefender.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiDefender(resourceName = "defendGetUser", unlockTimeout = 20000)
    public List<UserResponse> getUsers() {
        return this.userService.getUsers()
                .stream()
                .map(UserResponse::toResponse)
                .collect(toList());
    }

}
