package com.tghtechnology.posweb.data.repository;

import com.tghtechnology.posweb.data.entities.ActividadEmpleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActividadRepository extends JpaRepository<ActividadEmpleado, Long> {
}
