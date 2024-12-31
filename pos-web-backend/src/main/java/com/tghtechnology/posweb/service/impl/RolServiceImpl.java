package com.tghtechnology.posweb.service.impl;

import com.tghtechnology.posweb.data.dto.RolDto;
import com.tghtechnology.posweb.data.entities.Rol;
import com.tghtechnology.posweb.data.map.RolMapper;
import com.tghtechnology.posweb.data.repository.RolRepository;
import com.tghtechnology.posweb.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository rolRepository;
        
    @Override
    public List<RolDto> obtenerRoles() {
        List<Rol> roles = rolRepository.findAll();
        return roles.stream()
                        .map(RolMapper::toDto)
                        .collect(Collectors.toList());
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

    @Override
    public Rol editarRol(Long id, Rol rolActualizado) {
        // Verificar si el rol existe
        if (!rolRepository.existsById(id)) {
            throw new IllegalArgumentException("El rol con ID " + id + " no existe");
        }
        Rol rolExistente = rolRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("El rol con ID " + id + " no existe"));
        rolExistente.setNombreRol(rolActualizado.getNombreRol());
        return rolRepository.save(rolExistente);
    }

    @Override
    public Boolean existeRol(Long idRol){
        return rolRepository.existsById(idRol);
    }

}


