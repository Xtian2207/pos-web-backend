package com.tghtechnology.posweb.data.map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.tghtechnology.posweb.data.dto.RolDto;
import com.tghtechnology.posweb.data.entities.Rol;


public class RolMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Rol toEntity(RolDto rolDTO){
        return modelMapper.map(rolDTO, Rol.class);
    }

    public RolDto toDto (Rol rol){
        return modelMapper.map(rol, RolDto.class);
    }

}
