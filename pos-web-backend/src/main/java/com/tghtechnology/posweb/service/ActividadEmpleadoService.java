package com.tghtechnology.posweb.service;

import com.tghtechnology.posweb.data.entities.ActividadEmpleado;

import java.util.List;

public interface ActividadEmpleadoService {

    ActividadEmpleado registrarActividad(ActividadEmpleado actividadEmpleado);

    List<ActividadEmpleado> consultarActividadesPorEmpleado(Long idEmpleado);

    List<ActividadEmpleado> consultarActividadesPorVenta(Long idVenta);

    boolean eliminarActividad(Long idActividad);

    ActividadEmpleado actualizarActividad(Long idActividad, ActividadEmpleado actividadActualizada);

}
