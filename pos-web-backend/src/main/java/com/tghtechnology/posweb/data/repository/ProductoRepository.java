package com.tghtechnology.posweb.data.repository;

import com.tghtechnology.posweb.data.entities.EstadoProducto;
import com.tghtechnology.posweb.data.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Optional<Producto> findByNombreProducto(String nombreProducto);

    //Consultar un producto por su ID
    Optional<Producto> findByIdProducto(Long idProducto);

    //Consultar productos por su estado (Disponible, No Disponible)
    List<Producto> findByEstado(EstadoProducto estado);

    //Consultar productos por categoría
    List<Producto> findByCategoriaIdCategoria(Long categoriaId);

    Optional<Producto> findByCodigoBarrasUrl(String codigoBarrasUrl);

    Optional<Producto> findByCodigoBarras(String codigoBarras);

}
