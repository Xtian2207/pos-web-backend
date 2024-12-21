package com.tghtechnology.posweb.data.repository;

import com.tghtechnology.posweb.data.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolRepository extends JpaRepository<Rol, Long> {

    List<Rol> findAll();

    Rol findByIdRol(Long id);
}
