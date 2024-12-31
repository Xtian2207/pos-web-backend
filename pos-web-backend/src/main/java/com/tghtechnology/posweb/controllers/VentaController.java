package com.tghtechnology.posweb.controllers;

import com.tghtechnology.posweb.data.entities.Venta;
import com.tghtechnology.posweb.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    // Registrar una venta
    @PostMapping
    public ResponseEntity<Venta> registrarVenta(@RequestBody Venta venta) {
        ventaService.registrarVenta(venta);
        return ResponseEntity.ok().build();
    }

    // Consultar todas las ventas
    @GetMapping
    public ResponseEntity<List<Venta>> listarVentas() {
        List<Venta> ventas = ventaService.listarVentas();
        return ResponseEntity.ok(ventas);
    }

    // Obtener venta por ID
    @GetMapping("/{idVenta}")
    public ResponseEntity<Venta> obtenerVentaPorId(@PathVariable Long idVenta) {
        Optional<Venta> venta = ventaService.obtenerVentaPorId(idVenta);
        return venta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Actualizar una venta
    @PutMapping("/{idVenta}")
    public ResponseEntity<Venta> actualizarVenta(@PathVariable Long idVenta, @RequestBody Venta ventaActualizada) {
        Venta ventaActualizadaResult = ventaService.actualizarVenta(idVenta, ventaActualizada);
        return ResponseEntity.ok(ventaActualizadaResult);
    }

    // Eliminar una venta
    @DeleteMapping("/{idVenta}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long idVenta) {
        ventaService.eliminarVenta(idVenta);
        return ResponseEntity.noContent().build();
    }

}
