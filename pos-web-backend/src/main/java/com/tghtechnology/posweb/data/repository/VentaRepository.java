package com.tghtechnology.posweb.data.repository;

import com.tghtechnology.posweb.data.entities.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {

    @Query("select v from Venta v where month(v.fechaVenta) = :mes")
    List<Venta> findVentasbyMes(@Param("mes") int mes);
}
