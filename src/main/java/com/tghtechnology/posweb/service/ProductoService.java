package com.tghtechnology.posweb.service;

import com.tghtechnology.posweb.data.dto.ProductoDTO;
import com.tghtechnology.posweb.data.entities.EstadoProducto;
import com.tghtechnology.posweb.data.entities.Producto;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

public interface ProductoService {

        ProductoDTO registrarProducto(Long categoriaId, ProductoDTO productoDTO, MultipartFile multipartFile)
                        throws IOException;

        List<ProductoDTO> listarProductos();

        Optional<ProductoDTO> buscarPorNombre(String nombre);

        Optional<ProductoDTO> buscarPorId(Long idProducto);

        ProductoDTO actualizarProducto(Long idProducto, ProductoDTO productoActualizaDto, MultipartFile multipartFile)
                        throws IOException;

        Producto actualizarImagenProducto(MultipartFile file, Producto producto) throws IOException;

        void eliminarProducto(Long idProducto);

        ProductoDTO cambiarEstadoProducto(Long idProducto, EstadoProducto nuevoEstadoProducto);

        List<ProductoDTO> obtenerProductosPorEstado(EstadoProducto estadoProducto);

        List<ProductoDTO> obtenerProductosPorCategoria(Long categoriaId);

        ProductoDTO obtenerProductoPorImagenCodigoBarras(String imageUrl) throws IOException;

        ProductoDTO aumentarStockPorImagenCodigoBarras(String imageUrl) throws IOException;

        ProductoDTO buscarPorCodigoDeBarras(String codigoBarras);
}
