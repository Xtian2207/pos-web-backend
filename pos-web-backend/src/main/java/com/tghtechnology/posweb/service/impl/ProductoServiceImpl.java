package com.tghtechnology.posweb.service.impl;

import com.tghtechnology.posweb.data.dto.ProductoDTO;
import com.tghtechnology.posweb.data.entities.Categoria;
import com.tghtechnology.posweb.data.entities.EstadoProducto;
import com.tghtechnology.posweb.data.entities.Producto;
import com.tghtechnology.posweb.data.mapper.ProductoMapper;
import com.tghtechnology.posweb.data.repository.CategoriaRepository;
import com.tghtechnology.posweb.data.repository.ProductoRepository;
import com.tghtechnology.posweb.service.ProductoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    public ProductoDTO registrarProducto(Long categoriaId, ProductoDTO productoDTO) throws Exception {
        try {
            Categoria categoria = categoriaRepository.findById(categoriaId)
                    .orElseThrow(() -> new Exception("Categoría con ID " + categoriaId + " no encontrada"));

            Producto producto = productoMapper.toEntity(productoDTO);
            producto.setCategoria(categoria);

            Producto productoGuardado = productoRepository.save(producto);
            return productoMapper.toDTO(productoGuardado);
        } catch (DataIntegrityViolationException e) {
            log.error("Error al crear el producto: {}", e.getMessage());
            throw new Exception("Error al crear el producto. Verifique los datos ingresados.", e);
        } catch (Exception e) {
            log.error("Error al registrar producto: {}", e.getMessage());
            throw e;
        }
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
        Optional<Producto> productoExistente = productoRepository.findByIdProducto(idProducto);
        if (productoExistente.isPresent()) {
            Producto producto = productoExistente.get();
            productoMapper.toEntity(productoDTO);
            Producto productoActualizado = productoRepository.save(producto);
            return productoMapper.toDTO(productoActualizado);
        } else {
            return null;
        }
    }

    @Override
    public void eliminarProducto(Long idProducto) {
        productoRepository.deleteById(idProducto);
    }

    @Override
    public ProductoDTO cambiarEstadoProducto(Long idProducto, EstadoProducto nuevoEstadoProducto) {
        Optional<Producto> productoExistente = productoRepository.findByIdProducto(idProducto);
        if (productoExistente.isPresent()) {
            Producto producto = productoExistente.get();
            producto.setEstado(nuevoEstadoProducto);
            Producto productoActualizado = productoRepository.save(producto);
            return productoMapper.toDTO(productoActualizado);
        } else {
            return null;
        }
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
