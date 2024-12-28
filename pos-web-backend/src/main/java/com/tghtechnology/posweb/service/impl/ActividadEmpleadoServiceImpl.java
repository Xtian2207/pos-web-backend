package com.tghtechnology.posweb.service.impl;

import com.tghtechnology.posweb.data.entities.ActividadEmpleado;
import com.tghtechnology.posweb.data.entities.Usuario;
import com.tghtechnology.posweb.data.entities.Venta;
import com.tghtechnology.posweb.data.repository.ActividadEmpleadoRepository;
import com.tghtechnology.posweb.data.repository.UsuarioRepository;
import com.tghtechnology.posweb.data.repository.VentaRepository;
import com.tghtechnology.posweb.service.ActividadEmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActividadEmpleadoServiceImpl implements ActividadEmpleadoService {

    @Autowired
    private ActividadEmpleadoRepository actividadEmpleadoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @Override
    public ActividadEmpleado registrarActividad(Long idEmpleado, Long idVenta) {
        // Validar que el empleado y la venta existan
        Usuario empleado = validarExistenciaEntidadEmpleado(idEmpleado);
        Venta venta = validarExistenciaEntidadVenta(idVenta);

        // Crear la actividad del empleado
        ActividadEmpleado actividadEmpleado = new ActividadEmpleado();
        actividadEmpleado.setEmpleado(empleado);
        actividadEmpleado.setVenta(venta);

        // Guardar la actividad
        return actividadEmpleadoRepository.save(actividadEmpleado);
    }

    @Override
    public ActividadEmpleado actualizarActividad(Long idActividad, Long idEmpleado, Long idVenta) {
        Optional<ActividadEmpleado> actividadEmpleadoOptional = actividadEmpleadoRepository.findById(idActividad);
        if (!actividadEmpleadoOptional.isPresent()) {
            throw new IllegalArgumentException("Actividad no encontrada");
        }

        ActividadEmpleado actividadEmpleado = actividadEmpleadoOptional.get();

        // Actualizar las relaciones con el empleado y la venta si se proporcionan
        // nuevos IDs
        if (idEmpleado != null) {
            Usuario empleado = validarExistenciaEntidadEmpleado(idEmpleado);
            actividadEmpleado.setEmpleado(empleado);
        }

        if (idVenta != null) {
            Venta venta = validarExistenciaEntidadVenta(idVenta);
            actividadEmpleado.setVenta(venta);
        }

        return actividadEmpleadoRepository.save(actividadEmpleado);
    }

    // Consultar todas las actividades de un empleado
    @Override
    public List<ActividadEmpleado> consultarActividadesPorEmpleado(Long idEmpleado) {
        return actividadEmpleadoRepository.findAllByEmpleado_IdUsuario(idEmpleado);
    }

    // Consultar todas las actividades asociadas a una venta
    @Override
    public List<ActividadEmpleado> consultarActividadesPorVenta(Long idVenta) {
        return actividadEmpleadoRepository.findAllByVenta_IdVenta(idVenta);
    }

    @Override
    public boolean eliminarActividad(Long idActividad) {
        Optional<ActividadEmpleado> actividadEmpleadoOptional = actividadEmpleadoRepository.findById(idActividad);
        if (actividadEmpleadoOptional.isPresent()) {
            actividadEmpleadoRepository.deleteById(idActividad);
            return true;
        }
        return false;
    }

    // Validar que la entidad existe
    private Usuario validarExistenciaEntidadEmpleado(Long id) {
        Optional<Usuario> entidadOptional = usuarioRepository.findById(id);
        if (!entidadOptional.isPresent()) {
            throw new IllegalArgumentException("Empleado no encontrado");
        }
        return entidadOptional.get();
    }

    private Venta validarExistenciaEntidadVenta(Long id) {
        Optional<Venta> entidadOptional = ventaRepository.findById(id);
        if (!entidadOptional.isPresent()) {
            throw new IllegalArgumentException("Venta no encontrada");
        }
        return entidadOptional.get();
    }

}
