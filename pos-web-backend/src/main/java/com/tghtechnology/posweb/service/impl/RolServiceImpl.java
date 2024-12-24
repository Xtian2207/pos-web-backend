package com.tghtechnology.posweb.service.impl;

import com.tghtechnology.posweb.data.entities.Rol;
import com.tghtechnology.posweb.data.repository.RolRepository;
import com.tghtechnology.posweb.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository rolRepository;

    @Override
    public List<Rol> getAllRol(){
        return rolRepository.findAll();
    }
}
