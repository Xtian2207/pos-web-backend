package com.tghtechnology.posweb.data.repository;

import com.tghtechnology.posweb.data.entities.ActividadEmpleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActividadRepository extends JpaRepository<ActividadEmpleado, Long> {
}
