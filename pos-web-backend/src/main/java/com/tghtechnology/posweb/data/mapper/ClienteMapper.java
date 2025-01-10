package com.tghtechnology.posweb.data.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.tghtechnology.posweb.data.dto.ClienteDTO;
import com.tghtechnology.posweb.data.entities.Cliente;


@Component
public class ClienteMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Cliente toEntity(ClienteDTO clienteDTO){
        return modelMapper.map(clienteDTO, Cliente.class);
    }

    public ClienteDTO toDto (Cliente cliente){
        return modelMapper.map(cliente, ClienteDTO.class);
    }
}
