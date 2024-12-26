package com.tghtechnology.posweb.service.impl;

import com.tghtechnology.posweb.data.entities.Rol;
import com.tghtechnology.posweb.data.repository.RolRepository;
import com.tghtechnology.posweb.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository rolRepository;
        
    @Override
    public Optional<List<Rol>> obtenerRoles(){
        List<Rol> roles = rolRepository.findAll();            
        if (!roles.isEmpty()) {
            return Optional.of(roles);
        }else {
            return Optional.empty();
        }
    }

    @Override
    public void ingresarRol(Rol rol){
        Long idRol = rol.getIdRol();
        if(idRol!=0){
            rolRepository.save(rol);
        }
    }

    @Override
    public void eliminarRol(Long id){
        if (rolRepository.existsById(id)) {
            rolRepository.deleteById(id);
        }
    }

    @Override
    public Rol obtenerRolById(Long id) {
        if (id != 0) {
            Optional<Rol> optionalRol = rolRepository.findById(id);
            return optionalRol.orElse(null); 
        } else {
            return null;
        }
    }

    @Override
    public void editarRol(Rol rol){
        if (rolRepository.existsById(rol.getIdRol())) {
            rolRepository.save(rol);
        }
    }

    
}
