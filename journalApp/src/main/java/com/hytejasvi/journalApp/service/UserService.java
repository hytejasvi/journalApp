package com.hytejasvi.journalApp.service;

import com.hytejasvi.journalApp.Dto.UserDto;
import com.hytejasvi.journalApp.Entity.User;
import com.hytejasvi.journalApp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    //initializing the logger, and making it final and static so that it will remain same for all instances of this class.
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean createUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            //by default error, info and warning are enabled .
            logger.info("info for error occurred for : {} -> {}" , user.getUserName(),e.getLocalizedMessage());
            logger.error("Error occurred for : {} -> {}" , user.getUserName(),e.getMessage());
            logger.warn("warning for error occurred for : {}" , user.getUserName(),e);
            return false;
        }
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

    public void saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        userRepository.save(user);
    }
}
