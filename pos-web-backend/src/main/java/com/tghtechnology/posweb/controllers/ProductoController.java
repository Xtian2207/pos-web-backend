package com.tghtechnology.posweb.controllers;

import com.tghtechnology.posweb.data.entities.EstadoProducto;
import com.tghtechnology.posweb.data.entities.Producto;
import com.tghtechnology.posweb.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping("/registrar/{categoriaId}")
    public ResponseEntity<?> registrarProducto(@PathVariable Long categoriaId, @RequestBody Producto producto){
        try{
            Producto productoRegistrado = productoService.registrarProducto(categoriaId,producto);
            return ResponseEntity.status(HttpStatus.CREATED).body(productoRegistrado);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos() {
        List<Producto> productos = productoService.listarProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/buscar/nombre/{nombre}")
    public ResponseEntity<?> buscarPorNombre(@PathVariable String nombre) {
        Optional<Producto> producto = productoService.buscarPorNombre(nombre);
        return producto.isPresent() ? ResponseEntity.ok(producto.get()) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
    }

    @GetMapping("/buscar/id/{idProducto}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long idProducto){
        Optional<Producto> producto = productoService.buscarPorId(idProducto);
        return producto.isPresent() ? ResponseEntity.ok(producto.get()) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
    }

    @PutMapping("/actualizar/{idProducto}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long idProducto, @RequestBody Producto productoActualizado) {
        Producto producto = productoService.actualizarProducto(idProducto, productoActualizado);
        return producto != null ? ResponseEntity.ok(producto) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado para actualizar");
    }

    @DeleteMapping("/eliminar/{idProducto}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long idProducto) {
        try {
            productoService.eliminarProducto(idProducto);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al eliminar el producto");
        }
    }

    @PutMapping("/cambiar-estado/{idProducto}")
    public ResponseEntity<?> cambiarEstadoProducto(@PathVariable Long idProducto, @RequestBody EstadoProducto nuevoEstado) {
        Producto producto = productoService.cambiarEstadoProducto(idProducto, nuevoEstado);
        return producto != null ? ResponseEntity.ok(producto) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado para cambiar estado");
    }

    @GetMapping("/estado/{estadoProducto}")
    public ResponseEntity<List<Producto>> obtenerProductosPorEstado(@PathVariable EstadoProducto estadoProducto) {
        List<Producto> productos = productoService.obtenerProductosPorEstado(estadoProducto);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<Producto>> obtenerProductosPorCategoria(@PathVariable Long categoriaId) {
        List<Producto> productos = productoService.obtenerProductosPorCategoria(categoriaId);
        return ResponseEntity.ok(productos);
    }
}
