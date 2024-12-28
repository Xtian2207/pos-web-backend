package com.tghtechnology.posweb.controllers;

import com.tghtechnology.posweb.data.entities.ActividadEmpleado;
import com.tghtechnology.posweb.service.ActividadEmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actividades-empleado")
public class ActividadEmpleadoController {

    @Autowired
    private ActividadEmpleadoService actividadEmpleadoService;

    // Registrar una actividad para un empleado
    @PostMapping("/registrar")
    public ResponseEntity<ActividadEmpleado> registrarActividad(
            @RequestParam Long idEmpleado,
            @RequestParam Long idVenta) {
        try {
            ActividadEmpleado actividadEmpleado = actividadEmpleadoService.registrarActividad(idEmpleado, idVenta);
            return new ResponseEntity<>(actividadEmpleado, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Actualizar una actividad de un empleado
    @PutMapping("/actualizar/{idActividad}")
    public ResponseEntity<ActividadEmpleado> actualizarActividad(
            @PathVariable Long idActividad,
            @RequestParam(required = false) Long idEmpleado,
            @RequestParam(required = false) Long idVenta) {
        try {
            ActividadEmpleado actividadEmpleado = actividadEmpleadoService.actualizarActividad(idActividad, idEmpleado,
                    idVenta);
            return new ResponseEntity<>(actividadEmpleado, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Consultar todas las actividades de un empleado
    @GetMapping("/empleado/{idEmpleado}")
    public ResponseEntity<List<ActividadEmpleado>> consultarActividadesPorEmpleado(@PathVariable Long idEmpleado) {
        List<ActividadEmpleado> actividades = actividadEmpleadoService.consultarActividadesPorEmpleado(idEmpleado);
        if (actividades.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(actividades, HttpStatus.OK);
    }

    // Consultar todas las actividades asociadas a una venta
    @GetMapping("/venta/{idVenta}")
    public ResponseEntity<List<ActividadEmpleado>> consultarActividadesPorVenta(@PathVariable Long idVenta) {
        List<ActividadEmpleado> actividades = actividadEmpleadoService.consultarActividadesPorVenta(idVenta);
        if (actividades.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(actividades, HttpStatus.OK);
    }

    // Eliminar una actividad de un empleado
    @DeleteMapping("/eliminar/{idActividad}")
    public ResponseEntity<HttpStatus> eliminarActividad(@PathVariable Long idActividad) {
        boolean eliminado = actividadEmpleadoService.eliminarActividad(idActividad);
        if (eliminado) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
