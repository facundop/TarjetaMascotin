package com.facundoprecentado.repository;

import com.facundoprecentado.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findById(Long id);
    User findByEmail(String email);
    User findByDni(Long dni);
    List<User> findByLastName(String lastName);
}

