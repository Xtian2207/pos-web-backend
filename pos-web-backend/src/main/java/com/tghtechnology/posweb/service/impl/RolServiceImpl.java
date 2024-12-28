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
    public List<Rol> obtenerRoles() {
        return rolRepository.findAll();
    }

    @Override
    public void ingresarRol(Rol rol){
        
        if(rolRepository.existsByNombreRol(rol.getNombreRol())){
            throw new IllegalArgumentException("El rol ya existe");
        }

        rolRepository.save(rol);

    }

    @Override
    public void eliminarRol(Long id){
        if (rolRepository.existsById(id)) {
            rolRepository.deleteById(id);
        }else{
            throw new IllegalArgumentException("El rol con ID " + id + " no existe");
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

    public Rol editarRol(Long id, Rol rolActualizado) {
        // Verificar si el rol existe
        if (!rolRepository.existsById(id)) {
            throw new IllegalArgumentException("El rol con ID " + id + " no existe");
        }
        Rol rolExistente = rolRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("El rol con ID " + id + " no existe"));
        rolExistente.setNombreRol(rolActualizado.getNombreRol());
        return rolRepository.save(rolExistente);
    }
}


