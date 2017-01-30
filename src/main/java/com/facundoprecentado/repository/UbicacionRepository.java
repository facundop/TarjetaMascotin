package com.facundoprecentado.repository;

import com.facundoprecentado.domain.Ubicacion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by facundo on 28/1/2017.
 */
public interface UbicacionRepository extends CrudRepository<Ubicacion, String> {
    List<Ubicacion> findAll();
    List<Ubicacion> findByIdDepartamento(int idDepartamento);
    List<Ubicacion> findByIdProvincia(int idProvincia);
    List<Ubicacion> findByIdDistrito(int idDistrito);

    @Query("select distinct(u) from Ubicacion u group by u.idDepartamento")
    List<Ubicacion> findDepartamentos();

    @Query("select distinct(u) from Ubicacion u group by u.idProvincia")
    List<Ubicacion> findProvincias();

    @Query("select distinct(u) from Ubicacion u where u.idDepartamento = ?1 group by u.idProvincia")
    List<Ubicacion> findProvinciasByDepartamento(int idDepartamento);

    @Query("select distinct(u) from Ubicacion u group by u.idDistrito")
    List<Ubicacion> findDistritos();

    @Query("select distinct(u) from Ubicacion u where u.idDepartamento = ?1 and u.idProvincia = ?2 group by u.idDistrito")
    List<Ubicacion> findDistritosByDepartamentoAndProvincia(int idDepartamento, int idProvincia);
}
