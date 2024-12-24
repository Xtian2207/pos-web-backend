package com.tghtechnology.posweb.service;

import com.tghtechnology.posweb.data.entities.EstadoProducto;
import com.tghtechnology.posweb.data.entities.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {

    Producto registrarProducto(Producto producto);
    List<Producto> listarProductos();

    Optional<Producto> buscarPorNombre(String nombre);

    Optional<Producto> buscarPorId(Long idProducto);

    Producto actualizarProducto(Long idProducto, Producto productoActualizado);

    void eliminarProducto(Long idProducto);

    Producto cambiarEstadoProducto(Long idProducto, EstadoProducto nuevoEstadoProducto);

    List<Producto> obtenerProductosPorEstado(EstadoProducto estadoProducto);

    List<Producto> obtenerProductosPorCategoria(Long categoriaId);
}
