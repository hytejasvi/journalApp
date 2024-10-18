package com.hytejasvi.journalApp.controller;

import com.hytejasvi.journalApp.Dto.UserDto;
import com.hytejasvi.journalApp.Entity.User;
import com.hytejasvi.journalApp.cache.AppCache;
import com.hytejasvi.journalApp.service.UserService;
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

    @Autowired
    private AppCache appCache;
    @GetMapping("/all-users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUsers = userService.getAll();
        if (allUsers != null && !allUsers.isEmpty()) {
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    public void createAdminUser(@RequestBody User user) {
        userService.saveAdmin(user);
    }

    @GetMapping("clear-app-cache")
    /*this end point can be called to reload / re-initialize the AppCache.
     * This can be a useful scenario where there is any change in the db and we have to reload the AppCache without
       reloading the application.*/
    public void clearAppCache() {
        appCache.init();
    }
}
