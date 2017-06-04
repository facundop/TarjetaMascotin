package com.facundoprecentado.repository;

import com.facundoprecentado.domain.Asociado;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AsociadoRepository extends CrudRepository<Asociado, String> {
        List<Asociado> findAll();
        List<Asociado> findAsociadosByEnabled(boolean flag);
        Asociado findById(Long id);
        Asociado findByUsername(String username);
}