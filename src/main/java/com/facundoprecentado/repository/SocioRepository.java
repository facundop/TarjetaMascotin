package com.facundoprecentado.repository;

import com.facundoprecentado.domain.Socio;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SocioRepository extends CrudRepository<Socio, String> {
    List<Socio> findAll();
    Socio findByUsername(String username);
    Socio findById(Long id);
}