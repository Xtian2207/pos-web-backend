package com.tghtechnology.posweb.controllers;

import com.tghtechnology.posweb.data.dto.VentaDTO;
import com.tghtechnology.posweb.service.ExcelReportService;
import com.tghtechnology.posweb.service.VentaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @Autowired
    private ExcelReportService excelReportService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/registrar")
    public ResponseEntity<VentaDTO> registrarVenta(@RequestBody VentaDTO ventaDTO) {
        try {
            VentaDTO ventaRegistrada = ventaService.registrarVenta(ventaDTO);
            messagingTemplate.convertAndSend("/topic/ventas",
                    "Nueva venta registrada: " + ventaRegistrada.getIdVenta());
            return new ResponseEntity<>(ventaRegistrada, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<VentaDTO>> listarVentas() {
        List<VentaDTO> ventas = ventaService.listarVentas();
        return new ResponseEntity<>(ventas, HttpStatus.OK);
    }

    @GetMapping("/{idVenta}")
    public ResponseEntity<VentaDTO> obtenerVentaPorId(@PathVariable Long idVenta) {
        Optional<VentaDTO> venta = ventaService.obtenerVentaPorId(idVenta);
        return venta.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/actualizar/{idVenta}")
    public ResponseEntity<VentaDTO> actualizarVenta(@PathVariable Long idVenta, @RequestBody VentaDTO ventaDTO) {
        try {
            VentaDTO ventaActualizada = ventaService.actualizarVenta(idVenta, ventaDTO);
            return new ResponseEntity<>(ventaActualizada, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/eliminar/{idVenta}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long idVenta) {
        try {
            ventaService.eliminarVenta(idVenta);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<VentaDTO>> obtenerVentasPorUsuario(@PathVariable Long usuarioId) {
        List<VentaDTO> ventas = ventaService.obtenerVentasPorUsuario(usuarioId);
        return new ResponseEntity<>(ventas, HttpStatus.OK);
    }

    @GetMapping("/fechas")
    public ResponseEntity<List<VentaDTO>> obtenerVentasPorFecha(
            @RequestParam("fechaInicio") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin) {
        List<VentaDTO> ventas = ventaService.obtenerVentasPorFecha(fechaInicio, fechaFin);
        return new ResponseEntity<>(ventas, HttpStatus.OK);
    }

    @GetMapping("/reportes/excel")
    public ResponseEntity<ByteArrayResource> descargarInformDeVentas(
            @RequestParam(value = "fechaInicio", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
            @RequestParam(value = "fechaFin", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin,
            @RequestParam(value = "anio", required = false) Integer anio)
            throws IOException {
        List<VentaDTO> ventas;

        if (fechaInicio != null && fechaFin != null) {
            ventas = ventaService.obtenerVentasPorFecha(fechaInicio, fechaFin);
        } else if (anio != null) {
            ventas = ventaService.obtenerVentasPorAnio(anio);
        } else {
            ventas = ventaService.listarVentas();
        }

        byte[] excelContent = excelReportService.generateSalesReport(ventas);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "reporte_ventas.xlsx");
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(excelContent.length)
                .body(new ByteArrayResource(excelContent));

    }

}
