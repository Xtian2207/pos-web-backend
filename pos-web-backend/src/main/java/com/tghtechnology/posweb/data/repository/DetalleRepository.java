package com.tghtechnology.posweb.data.repository;

import com.tghtechnology.posweb.data.entities.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DetalleRepository extends JpaRepository<DetalleVenta, Long> {

    @Query("select d from DetalleVenta d where d.venta.idVenta = :idVenta")
    List<DetalleVenta> finByIdVenta(@Param("idVenta") Long idVenta);
}
