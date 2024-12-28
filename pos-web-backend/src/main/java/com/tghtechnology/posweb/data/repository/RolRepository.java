package com.tghtechnology.posweb.data.repository;

import com.tghtechnology.posweb.data.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

    @Query("select r from Rol r where nombreRol = :nombre")
    Rol findRolByName(@Param("nombre") String nombre);

    boolean existsByNombreRol(String name);
}
