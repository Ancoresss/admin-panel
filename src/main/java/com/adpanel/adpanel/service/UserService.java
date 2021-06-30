package com.adpanel.adpanel.service;

import com.adpanel.adpanel.model.User;

import java.util.Optional;

public interface UserService {
    void addUser(User user);
    Optional<User> getUser(String name);
}
