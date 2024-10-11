package com.hytejasvi.journalApp.service;

import com.hytejasvi.journalApp.Dto.UserDto;
import com.hytejasvi.journalApp.Entity.JournalEntry;
import com.hytejasvi.journalApp.Entity.User;
import com.hytejasvi.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void createUser(User user) {
        System.out.println("Entering createUser method");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        System.out.println("User before saving: " + user.toString());
        userRepository.save(user);
        System.out.println("User saved successfully");
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }


    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public void updateUser(String currentUserName, UserDto userDto) throws Exception {
        User currentUser = findByUserName(currentUserName);
        if (userDto.getUserName() != null && !userDto.getUserName().isEmpty()) {
            currentUser.setUserName(userDto.getUserName());
        }
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            currentUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        userRepository.save(currentUser);
    }

    public void deleteUserByUsername(String userName) {
        userRepository.deleteByUserName(userName);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
}
