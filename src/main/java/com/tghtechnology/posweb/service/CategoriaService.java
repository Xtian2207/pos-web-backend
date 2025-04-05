package com.tghtechnology.posweb.service;

import com.tghtechnology.posweb.data.dto.CategoriaDTO;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {

    CategoriaDTO crearCategoria(CategoriaDTO categoriaDTO);

    List<CategoriaDTO> obtenerTodasLasCategorias();

    Optional<CategoriaDTO> obtenerCategoriaPorId(Long idCategoria);

    CategoriaDTO actualizarCategoria(Long idCategoria, CategoriaDTO categoriaDTO);

    void eliminarCategoria(Long idCategoria);

}
