package com.example.demomockito.controller;

import com.example.demomockito.domain.Response;
import com.example.demomockito.domain.user.User;
import com.example.demomockito.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{userName}", produces = APPLICATION_JSON_VALUE)
    public Response<User> getUserInfo(@PathVariable final String userName) {
        Optional<User> userInfo = userService.getUserInfo(userName);
        return userInfo.map(user -> new Response<>("S", "0000", "oh, successful..", user))
                .orElseGet(() -> new Response<>("E", "9999", "oh, fail.."));
    }
}
