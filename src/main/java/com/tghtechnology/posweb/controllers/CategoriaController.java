package com.tghtechnology.posweb.controllers;

import com.tghtechnology.posweb.data.dto.CategoriaDTO;
import com.tghtechnology.posweb.exceptions.ResourceNotFoundException;
import com.tghtechnology.posweb.service.CategoriaService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<CategoriaDTO> crearCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO) {
        CategoriaDTO nuevaCategoria = categoriaService.crearCategoria(categoriaDTO);
        return new ResponseEntity<>(nuevaCategoria, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> obtenerTodasLasCategorias() {
        List<CategoriaDTO> categorias = categoriaService.obtenerTodasLasCategorias();
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }

    @GetMapping("/{idCategoria}")
    public ResponseEntity<CategoriaDTO> obtenerCategoriaPorId(@PathVariable Long idCategoria) {
        Optional<CategoriaDTO> categoriaOptional = categoriaService.obtenerCategoriaPorId(idCategoria);
        if (categoriaOptional.isPresent()) {
            return new ResponseEntity<>(categoriaOptional.get(), HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Categoría no encontrada");
        }
    }

    @PutMapping("/{idCategoria}")
    public ResponseEntity<CategoriaDTO> actualizarCategoria(@PathVariable Long idCategoria,
            @Valid @RequestBody CategoriaDTO categoriaDTO) {
        try {
            CategoriaDTO categoriaActualizada = categoriaService.actualizarCategoria(idCategoria, categoriaDTO);
            if (categoriaActualizada != null) {
                return new ResponseEntity<>(categoriaActualizada, HttpStatus.OK);
            } else {
                throw new ResourceNotFoundException("Categoría no encontrada para actualizar");
            }
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{idCategoria}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long idCategoria) {
        try {
            categoriaService.eliminarCategoria(idCategoria);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
