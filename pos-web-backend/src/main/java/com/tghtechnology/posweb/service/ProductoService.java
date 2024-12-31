package com.tghtechnology.posweb.service;

import com.tghtechnology.posweb.data.dto.ProductoDTO;
import com.tghtechnology.posweb.data.entities.EstadoProducto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {

    ProductoDTO registrarProducto(Long categoriaId, ProductoDTO productoDTO) throws Exception;

    List<ProductoDTO> listarProductos();

    Optional<ProductoDTO> buscarPorNombre(String nombre);

    Optional<ProductoDTO> buscarPorId(Long idProducto);

    ProductoDTO actualizarProducto(Long idProducto, ProductoDTO productoActualizaDto);

    void eliminarProducto(Long idProducto);

    ProductoDTO cambiarEstadoProducto(Long idProducto, EstadoProducto nuevoEstadoProducto);

    List<ProductoDTO> obtenerProductosPorEstado(EstadoProducto estadoProducto);

    List<ProductoDTO> obtenerProductosPorCategoria(Long categoriaId);
}
