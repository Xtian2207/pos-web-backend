package com.tghtechnology.posweb.data.repository;

import com.tghtechnology.posweb.data.entities.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    @Query("select v from Venta v where month(v.fechaVenta) = :mes")
    List<Venta> findVentasbyMes(@Param("mes") int mes);


    // Metodo para obtener ventas por ID de usuario (empleado o cajero)
    List<Venta> findByUsuarioId(Long usuarioId);

    // Metodo para obtener ventas por rango de fechas
    List<Venta> findVentasByFechaVentaBetween(Date fechaInicio, Date fechaFin);
}
