package com.tghtechnology.posweb.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tghtechnology.posweb.data.dto.ProductoDTO;
import com.tghtechnology.posweb.data.entities.EstadoProducto;
import com.tghtechnology.posweb.data.entities.Producto;
import com.tghtechnology.posweb.data.mapper.ProductoMapper;
import com.tghtechnology.posweb.exceptions.ResourceNotFoundException;
import com.tghtechnology.posweb.service.ProductoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoMapper productoMapper;

    @PostMapping("/registrar/{categoriaId}")
    public ResponseEntity<?> registrarProducto(
            @PathVariable Long categoriaId,
            @RequestParam("productoDTO") String productoDTOJson,
            @RequestParam("file") MultipartFile multipartFile) throws IOException {

        // Convierte el JSON en un objeto ProductoDTO
        ObjectMapper objectMapper = new ObjectMapper();
        ProductoDTO productoDTO = objectMapper.readValue(productoDTOJson, ProductoDTO.class);

        ProductoDTO productoRegistrado = productoService.registrarProducto(categoriaId, productoDTO, multipartFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoRegistrado);
    }

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> listarProductos() {
        List<ProductoDTO> productos = productoService.listarProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/buscar/nombre/{nombre}")
    public ResponseEntity<?> buscarPorNombre(@PathVariable String nombre) {
        Optional<ProductoDTO> producto = productoService.buscarPorNombre(nombre);
        return producto.isPresent() ? ResponseEntity.ok(producto.get())
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
    }

    @GetMapping("/buscar/id/{idProducto}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long idProducto) {
        Optional<ProductoDTO> producto = productoService.buscarPorId(idProducto);
        return producto.isPresent() ? ResponseEntity.ok(producto.get())
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
    }

    /*     
    @PutMapping("/{idProducto}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long idProducto, @RequestBody ProductoDTO productoDTO) {
        try {
            ProductoDTO productoActualizado = productoService.actualizarProducto(idProducto, productoDTO);
            return ResponseEntity.ok(productoActualizado);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    */


    @PutMapping("/{idProducto}")
public ResponseEntity<?> actualizarProducto(
        @PathVariable Long idProducto, 
        @RequestBody ProductoDTO productoDTO) {
    try {
        // Asegurar que el ID del DTO coincide con el de la URL
        if (productoDTO.getIdProducto() == null) {
            productoDTO.setIdProducto(idProducto);
        } else if (!productoDTO.getIdProducto().equals(idProducto)) {
            return ResponseEntity.badRequest().body("El ID del producto en la URL y en el cuerpo no coinciden");
        }

        ProductoDTO productoActualizado = productoService.actualizarProducto(idProducto, productoDTO);
        return ResponseEntity.ok(productoActualizado);
    } catch (ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}


    @DeleteMapping("/{idProducto}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long idProducto) {
        try {
            productoService.eliminarProducto(idProducto);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/estado/{idProducto}")
    public ResponseEntity<?> cambiarEstadoProducto(@PathVariable Long idProducto,
            @RequestBody EstadoProducto estadoProducto) {
        try {
            ProductoDTO productoActualizado = productoService.cambiarEstadoProducto(idProducto, estadoProducto);
            return ResponseEntity.ok(productoActualizado);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<ProductoDTO>> obtenerProductosPorEstado(@PathVariable EstadoProducto estado) {
        List<ProductoDTO> productos = productoService.obtenerProductosPorEstado(estado);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<ProductoDTO>> obtenerProductosPorCategoria(@PathVariable Long categoriaId) {
        List<ProductoDTO> productos = productoService.obtenerProductosPorCategoria(categoriaId);
        return ResponseEntity.ok(productos);
    }

    /*
     * @PutMapping("/{id}/imagen")
     * public ResponseEntity<Producto> actualizarProductoImagen(@PathVariable Long
     * id,
     * 
     * @RequestPart("file") MultipartFile file) throws IOException {
     * Optional<ProductoDTO> productoDTO = productoService.buscarPorId(id);
     * 
     * if (productoDTO.isPresent()) {
     * Producto producto = productoMapper.toEntity(productoDTO.get());
     * Producto productoActualizado = productoService.actualizarImagenProducto(file,
     * producto);
     * return new ResponseEntity<>(productoActualizado, HttpStatus.OK);
     * } else {
     * return new ResponseEntity<>(HttpStatus.NOT_FOUND);
     * }
     * }
     */

    @PutMapping("/{id}/imagen")
    public ResponseEntity<ProductoDTO> actualizarProductoImagen(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) throws IOException {

        // Buscar el producto por ID
        ProductoDTO productoDTO = productoService.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto con ID " + id + " no encontrado"));

        // Convertir DTO a Entidad
        Producto producto = productoMapper.toEntity(productoDTO);

        // Actualizar la imagen
        Producto productoActualizado = productoService.actualizarImagenProducto(file, producto);

        // Convertir la entidad actualizada a DTO
        ProductoDTO productoActualizadoDTO = productoMapper.toDTO(productoActualizado);
        return ResponseEntity.ok(productoActualizadoDTO);
    }

}
