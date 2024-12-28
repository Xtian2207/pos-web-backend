package com.tghtechnology.posweb.service.impl;

import com.tghtechnology.posweb.data.entities.Categoria;
import com.tghtechnology.posweb.data.entities.EstadoProducto;
import com.tghtechnology.posweb.data.entities.Producto;
import com.tghtechnology.posweb.data.repository.CategoriaRepository;
import com.tghtechnology.posweb.data.repository.ProductoRepository;
import com.tghtechnology.posweb.service.ProductoService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public Producto registrarProducto(Long categoriaId, Producto producto) throws Exception {
        try {
            Categoria categoria = categoriaRepository.findById(categoriaId)
                    .orElseThrow(() -> new Exception("Categoría con ID " + categoriaId + " no encontrada"));

            producto.setCategoria(categoria);
            return productoRepository.save(producto);
        } catch (DataIntegrityViolationException e) {
            log.error("Error al crear el producto: {}", e.getMessage());
            throw new Exception("Error al crear el producto. Verifique los datos ingresados.", e);
        } catch (Exception e) {
            log.error("Error al registrar producto: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Optional<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreProducto(nombre);
    }

    @Override
    public Optional<Producto> buscarPorId(Long idProducto) {
        return productoRepository.findByIdProducto(idProducto);
    }

    @Override
    public Producto actualizarProducto(Long idProducto, Producto productoActualizado) {
        Optional<Producto> productoExistente = productoRepository.findByIdProducto(idProducto);
        if (productoExistente.isPresent()) {
            Producto producto = productoExistente.get();

            // Copiar las propiedades del productoActualizado al producto existente
            // Esto no reemplazará propiedades nulas, evitamos copiar propiedades nulas
            BeanUtils.copyProperties(productoActualizado, producto, getNullProperties(productoActualizado));

            return productoRepository.save(producto);
        } else {
            return null;
        }
    }

    @Override
    public void eliminarProducto(Long idProducto) {
        productoRepository.deleteById(idProducto);
    }

    @Override
    public Producto cambiarEstadoProducto(Long idProducto, EstadoProducto nuevoEstadoProducto) {
        Optional<Producto> productoExistente = productoRepository.findByIdProducto(idProducto);
        if (productoExistente.isPresent()) {
            Producto producto = productoExistente.get();
            producto.setEstado(nuevoEstadoProducto);
            return productoRepository.save(producto);
        } else {
            return null;
        }
    }

    @Override
    public List<Producto> obtenerProductosPorEstado(EstadoProducto estadoProducto) {
        return productoRepository.findByEstado(estadoProducto);
    }

    @Override
    public List<Producto> obtenerProductosPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoriaIdCategoria(categoriaId);
    }

    // Método para obtener las propiedades que son null y no copiar esos valores
    private String[] getNullProperties(Producto productoActualizado) {
        return Arrays.stream(Producto.class.getDeclaredFields()) //Obtenemos todos los campos declarados de Producto
                .map(field -> {
                    try {
                        field.setAccessible(true); //Permite acceder a los campos privados de Producto
                        return field.get(productoActualizado) == null ? field.getName() : null;
                    } catch (Exception exception) {
                        return null;
                    }
                })
                .filter(Objects::nonNull) //Filtramos los objetos qe son null
                .toArray(String[]::new); //Convertimos ese flujo filtrado en un Array
    }
}
