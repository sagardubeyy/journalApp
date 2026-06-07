package com.star.journalApp.controller;

import com.star.journalApp.entity.User;
import com.star.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")

public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUser() {
        List<User> getall = userService.getall();
        if (getall != null && !getall.isEmpty()) {
            return new ResponseEntity<>(getall ,HttpStatus.OK );
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin")
    public void newAdmin(@RequestBody User admin){
        userService.saveAdmin(admin);
    }
}
