package com.facundoprecentado.service;

import com.facundoprecentado.domain.User;

import java.util.Collection;
import java.util.List;

public interface UserService {
    Collection<User> findAll();
    User findByUsername(String username);
    User create(User user);
}
