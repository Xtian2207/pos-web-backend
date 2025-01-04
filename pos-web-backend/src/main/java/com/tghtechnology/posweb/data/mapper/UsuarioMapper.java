package com.tghtechnology.posweb.data.mapper;

import com.tghtechnology.posweb.data.entities.Usuario;
import com.tghtechnology.posweb.data.dto.UserCreateDTO;
import com.tghtechnology.posweb.data.dto.UsuarioDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    @Autowired
    private ModelMapper modelMapper;



    // conversion con el dto de manejo
    public Usuario toEntity(UsuarioDto usuarioDTO){
        return modelMapper.map(usuarioDTO, Usuario.class);
    }

    public UsuarioDto toDto (Usuario usuario){
        return modelMapper.map(usuario, UsuarioDto.class);
    }


    // coonversion con el dto de creacion
    public Usuario toEntityCreate(UserCreateDTO usuarioDTO){
        return modelMapper.map(usuarioDTO, Usuario.class);
    }

    public UserCreateDTO toDtoCreate (Usuario usuario){
        return modelMapper.map(usuario, UserCreateDTO.class);
    }

}
