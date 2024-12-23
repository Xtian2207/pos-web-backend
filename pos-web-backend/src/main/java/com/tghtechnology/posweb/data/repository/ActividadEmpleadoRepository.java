package com.tghtechnology.posweb.data.repository;

import com.tghtechnology.posweb.data.entities.ActividadEmpleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActividadEmpleadoRepository extends JpaRepository<ActividadEmpleado, Long> {

    List<ActividadEmpleado> findAllByEmpleadoId(Long idEmpleado);

    List<ActividadEmpleado> findAllByVentaId(Long idVenta);

}
