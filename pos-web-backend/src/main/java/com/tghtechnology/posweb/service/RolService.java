package com.tghtechnology.posweb.service;

import com.tghtechnology.posweb.data.entities.Rol;
import com.tghtechnology.posweb.data.repository.RolRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public List<Rol> getAllRol(){
        return rolRepository.findAll();
    }
}
