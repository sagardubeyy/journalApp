package com.star.journalApp.controller;


import com.star.journalApp.entity.User;
import com.star.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUser(){
        return userService.getall();
    }

    @PostMapping("/create-user")
    public void addNewUser(@RequestBody User user){
        userService.saveNewUser(user);
    }
}
