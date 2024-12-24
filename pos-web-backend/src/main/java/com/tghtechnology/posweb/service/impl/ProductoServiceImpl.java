package com.tghtechnology.posweb.service.impl;

import com.tghtechnology.posweb.data.entities.EstadoProducto;
import com.tghtechnology.posweb.data.entities.Producto;
import com.tghtechnology.posweb.data.repository.ProductoRepository;
import com.tghtechnology.posweb.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Producto registrarProducto(Producto producto){
        return productoRepository.save(producto);
    }

    @Override
    public List<Producto> listarProductos(){
        return productoRepository.findAll();
    }

    @Override
    public Optional<Producto> buscarPorNombre(String nombre){
        return productoRepository.findByNombreProducto(nombre);
    }

    @Override
    public Optional<Producto> buscarPorId(Long idProducto) {
        return productoRepository.findByIdProducto(idProducto);
    }

    @Override
    public Producto actualizarProducto(Long idProducto, Producto productoActualizado) {
        Optional<Producto> productoExistente = productoRepository.findByIdProducto(idProducto);
        if(productoExistente.isPresent()){
            Producto producto = productoExistente.get();
            producto.setNombreProducto(productoActualizado.getNombreProducto());
            producto.setDescripcion(productoActualizado.getDescripcion());
            producto.setPrecio(productoActualizado.getPrecio());
            producto.setCantidad(productoActualizado.getCantidad());
            producto.setEstado(productoActualizado.getEstado());
            producto.setCategoria(productoActualizado.getCategoria());
            return productoRepository.save(producto);
        }
        else{
            return null;
        }
    }

    @Override
    public void eliminarProducto(Long idProducto) {
        productoRepository.deleteById(idProducto);
    }

    @Override
    public Producto cambiarEstadoProducto(Long idProducto, EstadoProducto nuevoEstadoProducto){
        Optional<Producto> productoExistente = productoRepository.findByIdProducto(idProducto);
        if(productoExistente.isPresent()){
            Producto producto = productoExistente.get();
            producto.setEstado(nuevoEstadoProducto);
            return productoRepository.save(producto);
        }
        else{
            return null;
        }
    }

    @Override
    public List<Producto> obtenerProductosPorEstado(EstadoProducto estadoProducto){
        return productoRepository.findByEstado(estadoProducto);
    }

    @Override
    public List<Producto> obtenerProductosPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoriaIdCategoria(categoriaId);
    }
}
