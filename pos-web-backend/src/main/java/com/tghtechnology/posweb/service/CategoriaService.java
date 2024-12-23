package com.tghtechnology.posweb.service;

import com.tghtechnology.posweb.data.entities.Categoria;
import com.tghtechnology.posweb.data.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria crearCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public List<Categoria> obtenerTodasLasCategorias() {
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> obtenerCategoriaPorId(Long idCategoria) {
        return categoriaRepository.findById(idCategoria);
    }

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

    public void eliminarCategoria(Long idCategoria) {
        categoriaRepository.deleteById(idCategoria);
    }
}
