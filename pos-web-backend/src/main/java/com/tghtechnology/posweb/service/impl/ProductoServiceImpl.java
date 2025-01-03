package com.tghtechnology.posweb.service.impl;

import com.tghtechnology.posweb.data.dto.ProductoDTO;
import com.tghtechnology.posweb.data.entities.Categoria;
import com.tghtechnology.posweb.data.entities.EstadoProducto;
import com.tghtechnology.posweb.data.entities.Producto;
import com.tghtechnology.posweb.data.mapper.ProductoMapper;
import com.tghtechnology.posweb.data.repository.CategoriaRepository;
import com.tghtechnology.posweb.data.repository.ProductoRepository;
import com.tghtechnology.posweb.exceptions.BadRequestException;
import com.tghtechnology.posweb.exceptions.ResourceNotFoundException;
import com.tghtechnology.posweb.service.ProductoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProductoMapper productoMapper;

    @Override
    public ProductoDTO registrarProducto(Long categoriaId, ProductoDTO productoDTO) {
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría con ID " + categoriaId + " no encontrada"));

        if (productoDTO.getPrecio() == null || productoDTO.getPrecio() <= 0) {
            throw new BadRequestException("El precio del producto debe ser mayor que 0");
        }

        Producto producto = productoMapper.toEntity(productoDTO);
        producto.setCategoria(categoria);

        Producto productoGuardado = productoRepository.save(producto);
        return productoMapper.toDTO(productoGuardado);
    }

    @Override
    public List<ProductoDTO> listarProductos() {
        List<Producto> productos = productoRepository.findAll();
        return productos.stream()
                .map(productoMapper::toDTO)
                .toList();
    }

    @Override
    public Optional<ProductoDTO> buscarPorNombre(String nombre) {
        Optional<Producto> producto = productoRepository.findByNombreProducto(nombre);
        return producto.map(productoMapper::toDTO);
    }

    @Override
    public Optional<ProductoDTO> buscarPorId(Long idProducto) {
        Optional<Producto> producto = productoRepository.findByIdProducto(idProducto);
        return producto.map(productoMapper::toDTO);
    }

    @Override
    public ProductoDTO actualizarProducto(Long idProducto, ProductoDTO productoDTO) {
        Producto producto = productoRepository.findByIdProducto(idProducto)
                .orElseThrow(() -> new ResourceNotFoundException("Producto con ID " + idProducto + " no encontrado"));

        // Actualizar los datos del producto con los del DTO
        productoMapper.toEntity(productoDTO, producto);
        Producto productoActualizado = productoRepository.save(producto);

        return productoMapper.toDTO(productoActualizado);
    }

    @Override
    public void eliminarProducto(Long idProducto) {
        productoRepository.findById(idProducto)
                .orElseThrow(() -> new ResourceNotFoundException("Producto con ID " + idProducto + " no encontrado"));
        productoRepository.deleteById(idProducto);
    }

    @Override
    public ProductoDTO cambiarEstadoProducto(Long idProducto, EstadoProducto nuevoEstadoProducto) {
        Producto producto = productoRepository.findByIdProducto(idProducto)
                .orElseThrow(() -> new ResourceNotFoundException("Producto con ID " + idProducto + " no encontrado"));

        producto.setEstado(nuevoEstadoProducto);
        Producto productoActualizado = productoRepository.save(producto);
        return productoMapper.toDTO(productoActualizado);
    }

    @Override
    public List<ProductoDTO> obtenerProductosPorEstado(EstadoProducto estadoProducto) {
        List<Producto> productos = productoRepository.findByEstado(estadoProducto);
        return productos.stream()
                .map(productoMapper::toDTO)
                .toList();
    }

    @Override
    public List<ProductoDTO> obtenerProductosPorCategoria(Long categoriaId) {
        List<Producto> productos = productoRepository.findByCategoriaIdCategoria(categoriaId);
        return productos.stream()
                .map(productoMapper::toDTO)
                .toList();
    }
}
