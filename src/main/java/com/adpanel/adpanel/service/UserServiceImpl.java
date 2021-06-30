package com.adpanel.adpanel.service;

import com.adpanel.adpanel.model.User;
import com.adpanel.adpanel.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> getUser(String name) {
        return userRepository.findByName(name);
    }
}
