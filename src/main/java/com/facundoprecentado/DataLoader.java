package com.facundoprecentado;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.facundoprecentado.domain.Role;
import com.facundoprecentado.domain.User;
import com.facundoprecentado.repository.RoleRepository;
import com.facundoprecentado.repository.UserRepository;

@Component
public class DataLoader {

	private UserRepository userRepository;
	private RoleRepository roleRepository;

	@Autowired
	public DataLoader(UserRepository userRepository) {
		this.userRepository = userRepository;
		// LoadRoles();
		LoadUsers();
	}

	private void LoadUsers() {
		User user = new User();
		user.setEnabled(true);
		user.setPassword("123");
		user.setType(3);
		user.setUsername("tarjetamascotin@gmail.com");
		
		userRepository.save(user);
	}

	// private void LoadRoles() {
	// List<Role> roles = new ArrayList<>();
	// Role admin = new Role();
	// admin.setId(0);
	// admin.setRole("ADMIN");
	//
	// Role socio = new Role();
	// admin.setId(1);
	// admin.setRole("SOCIO");
	//
	// Role asociado = new Role();
	// admin.setId(2);
	// admin.setRole("ASOCIADO");
	//
	// roles.add(admin);
	// roles.add(socio);
	// roles.add(asociado);
	//
	// roleRepository.save(roles);
	// }
}