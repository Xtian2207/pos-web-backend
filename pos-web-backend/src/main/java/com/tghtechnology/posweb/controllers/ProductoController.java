package com.tghtechnology.posweb.controllers;

import com.itextpdf.text.DocumentException;
import com.tghtechnology.posweb.data.dto.ProductoDTO;
import com.tghtechnology.posweb.data.entities.EstadoProducto;
import com.tghtechnology.posweb.data.entities.Producto;
import com.tghtechnology.posweb.data.mapper.ProductoMapper;
import com.tghtechnology.posweb.exceptions.BadRequestException;
import com.tghtechnology.posweb.exceptions.ResourceNotFoundException;
import com.tghtechnology.posweb.service.BarcodeService;
import com.tghtechnology.posweb.service.ProductoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    private BarcodeService barcodeService;

    @Autowired
    private ProductoMapper productoMapper;

    @PostMapping("/registrar/{categoriaId}")
    public ResponseEntity<?> registrarProducto(
            @PathVariable Long categoriaId,
            @RequestParam("nombreProducto") String nombreProducto,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("precio") Double precio,
            @RequestParam("cantidad") int cantidad,
            @RequestParam("estado") EstadoProducto estado,
            @RequestParam("file") MultipartFile multipartFile) throws IOException {

        // Crear el DTO manualmente
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setNombreProducto(nombreProducto);
        productoDTO.setDescripcion(descripcion);
        productoDTO.setPrecio(precio);
        productoDTO.setCantidad(cantidad);
        productoDTO.setEstado(estado);

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

    @PutMapping("/actualizar/{idProducto}")
    public ResponseEntity<?> actualizarProducto(
            @PathVariable Long idProducto,
            @RequestParam("nombreProducto") String nombreProducto,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("precio") Double precio,
            @RequestParam("cantidad") int cantidad,
            @RequestParam("estado") EstadoProducto estado,
            @RequestParam(value = "file", required = false) MultipartFile multipartFile) {

        try {
            // Crear el DTO manualmente
            ProductoDTO productoDTO = new ProductoDTO();
            productoDTO.setNombreProducto(nombreProducto);
            productoDTO.setDescripcion(descripcion);
            productoDTO.setPrecio(precio);
            productoDTO.setCantidad(cantidad);
            productoDTO.setEstado(estado);

            ProductoDTO productoActualizado = productoService.actualizarProducto(idProducto, productoDTO,
                    multipartFile);
            return ResponseEntity.ok(productoActualizado);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la imagen");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
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

    @GetMapping("/descargar-codigo-barras/{idProducto}")
    public ResponseEntity<byte[]> descargarCodigoBarrasPdf(@PathVariable Long idProducto, @RequestParam int copies) {
        try {
            Optional<ProductoDTO> productoOpt = productoService.buscarPorId(idProducto);
            if (productoOpt.isPresent()) {
                String barcodeText = productoOpt.get().getCodigoBarrasUrl(); // Asume que el código de barras está en la
                                                                             // URL
                byte[] pdfBytes = barcodeService.generateBarcodePdf(barcodeText, copies);

                HttpHeaders headers = new HttpHeaders(); // Crea un objeto para configurar las cabeceras HTTP
                headers.setContentType(MediaType.APPLICATION_PDF); // Indica que la respuesta es un PDF
                // Indica que el PDF debe descargarse con el nombre codigo_barras.pdf.
                headers.setContentDispositionFormData("attachment", "codigo_barras.pdf");
                // Configura la política de caché para evitar problemas con navegadores
                headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

                return ResponseEntity.ok()
                        .headers(headers)
                        .body(pdfBytes);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (IOException | DocumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/obtener-producto-imagen")
    public ResponseEntity<?> obtenerProductoPorImagenCodigoBarras(@RequestParam String imageUrl) {
        try {
            ProductoDTO producto = productoService.obtenerProductoPorImagenCodigoBarras(imageUrl);
            return ResponseEntity.ok(producto);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al procesar la imagen: " + e.getMessage());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/aumentar-stock-imagen")
    public ResponseEntity<?> aumentarStockPorImagenCodigoBarras(@RequestParam String imageUrl) {
        try {
            ProductoDTO productoActualizado = productoService.aumentarStockPorImagenCodigoBarras(imageUrl);
            return ResponseEntity.ok(productoActualizado);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al procesar la imagen: " + e.getMessage());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
