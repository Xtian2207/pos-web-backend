package com.tghtechnology.posweb.data.repository;

import com.tghtechnology.posweb.data.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

    Rol findByIdRol(Long id);
}
