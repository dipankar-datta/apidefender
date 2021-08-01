package com.ingeneral.apidefender.services;

import com.ingeneral.apidefender.data.entities.User;
import com.ingeneral.apidefender.data.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this.userRepository.getUsers();
    }


}
