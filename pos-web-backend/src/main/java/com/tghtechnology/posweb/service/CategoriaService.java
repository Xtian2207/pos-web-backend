package com.tghtechnology.posweb.service;

import com.tghtechnology.posweb.data.entities.Categoria;
import com.tghtechnology.posweb.data.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> getAllcategorias(){
        return categoriaRepository.findAll();
    }

}
