package com.tghtechnology.posweb.data.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tghtechnology.posweb.data.dto.CategoriaDTO;
import com.tghtechnology.posweb.data.entities.Categoria;

@Component
public class CategoriaMapper {

    @Autowired
    private ModelMapper modelMapper;

    public CategoriaDTO toDTO(Categoria categoria) {
        return modelMapper.map(categoria, CategoriaDTO.class);
    }

    public Categoria toEntity(CategoriaDTO categoriaDTO) {
        return modelMapper.map(categoriaDTO, Categoria.class);
    }

}
