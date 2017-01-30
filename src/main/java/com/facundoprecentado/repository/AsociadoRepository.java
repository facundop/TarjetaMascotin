package com.facundoprecentado.repository;

import com.facundoprecentado.domain.Asociado;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by facundo on 28/1/2017.
 */
public interface AsociadoRepository extends CrudRepository<Asociado, String> {
        List<Asociado> findAll();
        Asociado findByIdAsociado(int id);
        List<Asociado> findByIdDistrito(int idDistrito);
}