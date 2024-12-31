package com.tghtechnology.posweb.data.map;

import com.tghtechnology.posweb.data.entities.Usuario;

import java.util.Set;

import com.tghtechnology.posweb.data.dto.RolDto;
import com.tghtechnology.posweb.data.dto.UsuarioDto;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Usuario toEntity(UsuarioDto usuarioDTO){
        return modelMapper.map(usuarioDTO, Usuario.class);
    }
}
