package com.facundoprecentado.repository;

import com.facundoprecentado.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
}

