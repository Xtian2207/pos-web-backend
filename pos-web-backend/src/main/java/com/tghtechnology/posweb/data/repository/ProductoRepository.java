package com.tghtechnology.posweb.data.repository;

import com.tghtechnology.posweb.data.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByStockEquals(int stock);
}
