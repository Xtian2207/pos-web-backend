package com.tghtechnology.posweb.service.impl;

import com.tghtechnology.posweb.data.dto.ProductoDTO;
import com.tghtechnology.posweb.data.entities.Categoria;
import com.tghtechnology.posweb.data.entities.EstadoProducto;
import com.tghtechnology.posweb.data.entities.Imagen;
import com.tghtechnology.posweb.data.entities.Producto;
import com.tghtechnology.posweb.data.mapper.ProductoMapper;
import com.tghtechnology.posweb.data.repository.CategoriaRepository;
import com.tghtechnology.posweb.data.repository.ProductoRepository;
import com.tghtechnology.posweb.exceptions.BadRequestException;
import com.tghtechnology.posweb.exceptions.ResourceNotFoundException;
import com.tghtechnology.posweb.service.BarcodeService;
import com.tghtechnology.posweb.service.ImagenService;
import com.tghtechnology.posweb.service.ProductoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private ImagenService imagenService;

    @Autowired
    private BarcodeService barcodeService;

    @Autowired
    private ProductoMapper productoMapper;

    @Override
    public ProductoDTO registrarProducto(Long categoriaId, ProductoDTO productoDTO, MultipartFile multipartFile)
            throws IOException {
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría con ID " + categoriaId + " no encontrada"));

        if (productoDTO.getPrecio() == null || productoDTO.getPrecio() <= 0) {
            throw new BadRequestException("El precio del producto debe ser mayor que 0");
        }

        Producto producto = productoMapper.toEntity(productoDTO);
        producto.setCategoria(categoria);

        if (multipartFile != null && !multipartFile.isEmpty()) {
            Imagen imagen = imagenService.uploadImage(multipartFile);
            producto.setImagen(imagen);
        }

        String barcodeUrl = barcodeService.generateAndUploadBarcode();
        producto.setCodigoBarrasUrl(barcodeUrl);

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
    public ProductoDTO actualizarProducto(Long idProducto, ProductoDTO productoDTO,MultipartFile multipartFile) throws IOException {
        Producto productoExistente = productoRepository.findById(idProducto)
                .orElseThrow(() -> new ResourceNotFoundException("Producto con ID " + idProducto + " no encontrado"));
    
        // Actualizamos solo los campos que pueden cambiar
        productoExistente.setNombreProducto(productoDTO.getNombreProducto());
        productoExistente.setDescripcion(productoDTO.getDescripcion());
        productoExistente.setPrecio(productoDTO.getPrecio());
        productoExistente.setCantidad(productoDTO.getCantidad());
        productoExistente.setEstado(productoDTO.getEstado());
    
        // Mantenemos la categoría si no se envía una nueva
        if (productoDTO.getCategoria() != null && productoDTO.getCategoria().getIdCategoria() != null) {
            Categoria categoria = categoriaRepository.findById(productoDTO.getCategoria().getIdCategoria())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
            productoExistente.setCategoria(categoria);
        }

        if(multipartFile != null && !multipartFile.isEmpty()){
            if(productoExistente.getImagen() != null){
                imagenService.deleteImage(productoExistente.getImagen());
            }

            Imagen nuevaImagen = imagenService.uploadImage(multipartFile);
            productoExistente.setImagen(nuevaImagen);
        }
    
        // Guardamos el producto actualizado
        Producto productoActualizado = productoRepository.save(productoExistente);
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

    @Override
    public Producto actualizarImagenProducto(MultipartFile file, Producto producto) throws IOException {
        if (producto.getImagen() != null) {
            imagenService.deleteImage(producto.getImagen());
        }
        Imagen nuevaImagen = imagenService.uploadImage(file);
        producto.setImagen(nuevaImagen);
        return productoRepository.save(producto);
    }

}
