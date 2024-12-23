package com.tghtechnology.posweb.service;

import com.tghtechnology.posweb.data.entities.ActividadEmpleado;
import com.tghtechnology.posweb.data.entities.Usuario;
import com.tghtechnology.posweb.data.entities.Venta;
import com.tghtechnology.posweb.data.repository.ActividadEmpleadoRepository;
import com.tghtechnology.posweb.data.repository.UsuarioRepository;
import com.tghtechnology.posweb.data.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActividadEmpleadoService {

    @Autowired
    private ActividadEmpleadoRepository actividadEmpleadoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private VentaRepository ventaRepository;


    public ActividadEmpleado registrarActividad(ActividadEmpleado actividadEmpleado) {
        // Validar que el empleado y la venta existan
        validarExistenciaEntidad(actividadEmpleado.getEmpleado(), "Empleado");
        validarExistenciaEntidad(actividadEmpleado.getVenta(), "Venta");

        // Establecer la relación entre las entidades
        Usuario empleado = actividadEmpleado.getEmpleado();
        Venta venta = actividadEmpleado.getVenta();

        // Establecer las relaciones mediante set
        // En este caso no es necesario un set explícito para actividades, solo con las entidades relacionadas es suficiente.
        actividadEmpleado.setEmpleado(empleado);
        actividadEmpleado.setVenta(venta);

        // Guardar la actividad
        return actividadEmpleadoRepository.save(actividadEmpleado);
    }

    // Consultar todas las actividades de un empleado
    public List<ActividadEmpleado> consultarActividadesPorEmpleado(Long idEmpleado) {
        return actividadEmpleadoRepository.findAllByEmpleadoId(idEmpleado);
    }

    // Consultar todas las actividades asociadas a una venta
    public List<ActividadEmpleado> consultarActividadesPorVenta(Long idVenta) {
        return actividadEmpleadoRepository.findAllByVentaId(idVenta);
    }

    public boolean eliminarActividad(Long idActividad) {
        Optional<ActividadEmpleado> actividadEmpleadoOptional = actividadEmpleadoRepository.findById(idActividad);
        if (actividadEmpleadoOptional.isPresent()) {
            // Eliminar la actividad directamente, sin necesidad de modificar las relaciones manualmente
            actividadEmpleadoRepository.deleteById(idActividad);
            return true;
        }
        return false;
    }

    // Actualizar una actividad
    public ActividadEmpleado actualizarActividad(Long idActividad, ActividadEmpleado actividadActualizada) {
        Optional<ActividadEmpleado> actividadEmpleadoOptional = actividadEmpleadoRepository.findById(idActividad);
        if (!actividadEmpleadoOptional.isPresent()) {
            throw new IllegalArgumentException("Actividad no encontrada");
        }

        ActividadEmpleado actividadEmpleado = actividadEmpleadoOptional.get();

        // Actualizar las relaciones con el empleado y la venta
        if (actividadActualizada.getEmpleado() != null) {
            actividadEmpleado.setEmpleado(actividadActualizada.getEmpleado());
        }

        if (actividadActualizada.getVenta() != null) {
            actividadEmpleado.setVenta(actividadActualizada.getVenta());
        }

        return actividadEmpleadoRepository.save(actividadEmpleado);
    }

    // Validar que la entidad existe
    private void validarExistenciaEntidad(Object entidad, String nombreEntidad) {
        if (entidad == null) {
            throw new IllegalArgumentException(nombreEntidad + " no especificado o inválido");
        }

        Optional<?> entidadOptional;

        if (nombreEntidad.equals("Empleado")) {
            entidadOptional = usuarioRepository.findById(((Usuario) entidad).getIdUsuario());
        } else if (nombreEntidad.equals("Venta")) {
            entidadOptional = ventaRepository.findById(((Venta) entidad).getIdVenta());
        } else {
            throw new IllegalArgumentException("Entidad no válida para la validación");
        }

        if (!entidadOptional.isPresent()) {
            throw new IllegalArgumentException(nombreEntidad + " no encontrado");
        }
    }
}
