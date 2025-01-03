package com.tghtechnology.posweb.service.impl;

import com.tghtechnology.posweb.data.dto.CategoriaDTO;
import com.tghtechnology.posweb.data.entities.Categoria;
import com.tghtechnology.posweb.data.mapper.CategoriaMapper;
import com.tghtechnology.posweb.data.repository.CategoriaRepository;
import com.tghtechnology.posweb.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaMapper categoriaMapper;

    @Override
    public CategoriaDTO crearCategoria(CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaMapper.toEntity(categoriaDTO);
        Categoria nuevaCategoria = categoriaRepository.save(categoria);
        return categoriaMapper.toDTO(nuevaCategoria);
    }

    @Override
    public List<CategoriaDTO> obtenerTodasLasCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return categorias.stream()
                .map(categoriaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CategoriaDTO> obtenerCategoriaPorId(Long idCategoria) {
        Optional<Categoria> categoria = categoriaRepository.findById(idCategoria);
        return categoria.map(categoriaMapper::toDTO);
    }

    @Override
    public CategoriaDTO actualizarCategoria(Long idCategoria, CategoriaDTO categoriaDTO) {
        Optional<Categoria> categoriaExistente = categoriaRepository.findById(idCategoria);
        if (categoriaExistente.isPresent()) {
            Categoria categoria = categoriaExistente.get();
            categoria.setNombreCategoria(categoriaDTO.getNombreCategoria());
            Categoria categoriaActualizada = categoriaRepository.save(categoria);
            return categoriaMapper.toDTO(categoriaActualizada);
        } else {
            return null;
        }
    }

    @Override
    public void eliminarCategoria(Long idCategoria) {
        categoriaRepository.deleteById(idCategoria);
    }
}
