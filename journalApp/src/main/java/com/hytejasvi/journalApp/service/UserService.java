package com.hytejasvi.journalApp.service;

import com.hytejasvi.journalApp.Entity.User;
import com.hytejasvi.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void createUser(User user) {
        userRepository.save(user);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public void updateUser(String currentUserName, User user) throws Exception {
        User currentUser = findByUserName(currentUserName);
        if (currentUser != null) {
            if (user.getUserName() != null) {
                currentUser.setUserName((!user.getUserName().isEmpty()) ?
                        user.getUserName() : currentUserName);
            }
            if (user.getPassword() != null) {
                currentUser.setPassword((!user.getPassword().isEmpty()) ?
                        user.getPassword() : currentUserName);
            }
            userRepository.save(currentUser);
        } else {
            throw new Exception("User Dosen't exist");
        }
    }
}
