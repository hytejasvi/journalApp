package com.hytejasvi.journalApp.controller;

import com.hytejasvi.journalApp.Dto.UserDto;
import com.hytejasvi.journalApp.api.response.WeatherResponse;
import com.hytejasvi.journalApp.service.UserService;
import com.hytejasvi.journalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;

    @PutMapping()
    public ResponseEntity<String> updateUser(@RequestBody UserDto userDto) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            System.out.println(authentication.getName());  // Prints the username
            System.out.println(authentication.getAuthorities());  // Prints the roles or authorities
            userService.updateUser(userName, userDto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteUserByUserName() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userService.deleteUserByUsername(authentication.getName());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> greetings() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeather("Bangalore");
        String msg = "";
        if (weatherResponse != null) {
            System.out.println("getFeelsLike: "+weatherResponse.getCurrent().getFeelsLike());
            System.out.println();
            msg = " Weather at Bangalore feels like: "+ weatherResponse.getCurrent().getFeelsLike();
        }
        return new ResponseEntity<>("Hi "+ authentication.getName() + msg, HttpStatus.OK);
    }
}
