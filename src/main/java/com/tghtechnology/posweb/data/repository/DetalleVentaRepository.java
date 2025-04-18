package com.tghtechnology.posweb.data.repository;

import com.tghtechnology.posweb.data.entities.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {

    @Query("select d from DetalleVenta d where d.venta.idVenta = :idVenta")
    List<DetalleVenta> finByIdVenta(@Param("idVenta") Long idVenta);
}
