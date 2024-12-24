package com.tghtechnology.posweb.service;

import com.tghtechnology.posweb.data.entities.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {

    Categoria crearCategoria(Categoria categoria);

    List<Categoria> obtenerTodasLasCategorias();

    Optional<Categoria> obtenerCategoriaPorId(Long idCategoria);

    Categoria actualizarCategoria(Long idCategoria,Categoria categoriaActualizada);

    void eliminarCategoria(Long idCategoria);

}
