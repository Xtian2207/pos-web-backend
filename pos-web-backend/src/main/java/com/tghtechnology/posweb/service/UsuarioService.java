package com.tghtechnology.posweb.service;

import com.tghtechnology.posweb.data.entities.Usuario;
import com.tghtechnology.posweb.data.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> getAllusers(){
        return usuarioRepository.findAll();
    }

}
