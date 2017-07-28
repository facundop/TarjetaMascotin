package com.facundoprecentado.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facundoprecentado.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	Role findByRole(String role);

}