package com.tghtechnology.posweb.service.impl;

import com.tghtechnology.posweb.data.entities.Categoria;
import com.tghtechnology.posweb.data.repository.CategoriaRepository;
import com.tghtechnology.posweb.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public Categoria crearCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public List<Categoria> obtenerTodasLasCategorias() {
        return categoriaRepository.findAll();
    }

    @Override
    public Optional<Categoria> obtenerCategoriaPorId(Long idCategoria) {
        return categoriaRepository.findById(idCategoria);
    }

    @Override
    public Categoria actualizarCategoria(Long idCategoria,Categoria categoriaActualizada){
        Optional<Categoria> categoriaExistente = categoriaRepository.findById(idCategoria);
        if (categoriaExistente.isPresent()){
            Categoria categoria = categoriaExistente.get();
            categoria.setNombreCategoria(categoriaActualizada.getNombreCategoria());
            return categoriaRepository.save(categoria);
        }
        else{
            return null;
        }
    }

    @Override
    public void eliminarCategoria(Long idCategoria) {
        categoriaRepository.deleteById(idCategoria);
    }
}
