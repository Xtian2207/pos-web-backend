package com.tghtechnology.posweb.service;

import com.tghtechnology.posweb.data.entities.ActividadEmpleado;

import java.util.List;

public interface ActividadEmpleadoService {

    ActividadEmpleado registrarActividad(Long idEmpleado, Long idVenta);

    List<ActividadEmpleado> consultarActividadesPorEmpleado(Long idEmpleado);

    List<ActividadEmpleado> consultarActividadesPorVenta(Long idVenta);

    boolean eliminarActividad(Long idActividad);

    ActividadEmpleado actualizarActividad(Long idActividad, Long idEmpleado, Long idVenta);

}
