package com.facundoprecentado.repository;

import com.facundoprecentado.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, String> {
    List<User> findAll();
    User findByUsername(String id);
}

