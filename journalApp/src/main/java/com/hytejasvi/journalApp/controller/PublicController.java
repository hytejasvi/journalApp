package com.hytejasvi.journalApp.controller;

import com.hytejasvi.journalApp.Entity.User;
import com.hytejasvi.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    UserService userService;
    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if (userService.createUser(user)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("UserName Already Exists", HttpStatus.BAD_REQUEST);
        }
    }
}
