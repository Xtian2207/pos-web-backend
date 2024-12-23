package com.tghtechnology.posweb.service;

import com.tghtechnology.posweb.data.entities.EstadoProducto;
import com.tghtechnology.posweb.data.entities.Producto;
import com.tghtechnology.posweb.data.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public Producto registrarProducto(Producto producto){
        return productoRepository.save(producto);
    }

    public List<Producto> listarProductos(){
        return productoRepository.findAll();
    }

    public Optional<Producto> buscarPorNombre(String nombre){
        return productoRepository.findByNombreProducto(nombre);
    }

    public Optional<Producto> buscarPorId(Long idProducto) {
        return productoRepository.findByIdProducto(idProducto);
    }

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

    public void eliminarProducto(Long idProducto) {
        productoRepository.deleteById(idProducto);
    }

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

    public List<Producto> obtenerProductosPorEstado(EstadoProducto estadoProducto){
        return productoRepository.findByEstado(estadoProducto);
    }

    public List<Producto> obtenerProductosPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoriaIdCategoria(categoriaId);
    }
}
