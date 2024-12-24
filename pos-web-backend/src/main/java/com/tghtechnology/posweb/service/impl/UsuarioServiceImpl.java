package com.tghtechnology.posweb.service.impl;

import com.tghtechnology.posweb.data.entities.Usuario;
import com.tghtechnology.posweb.data.repository.UsuarioRepository;
import com.tghtechnology.posweb.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> getAllusers(){
        return usuarioRepository.findAll();
    }

}
