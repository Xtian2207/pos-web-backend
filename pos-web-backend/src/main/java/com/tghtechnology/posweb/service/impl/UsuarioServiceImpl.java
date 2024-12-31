package com.tghtechnology.posweb.service.impl;

import com.tghtechnology.posweb.data.dto.RolDto;
import com.tghtechnology.posweb.data.dto.UsuarioDto;
import com.tghtechnology.posweb.data.entities.EstadoUsuario;
import com.tghtechnology.posweb.data.entities.Rol;
import com.tghtechnology.posweb.data.entities.Usuario;
import com.tghtechnology.posweb.data.map.RolMapper;
import com.tghtechnology.posweb.data.map.UsuarioMapper;
import com.tghtechnology.posweb.data.repository.RolRepository;
import com.tghtechnology.posweb.data.repository.UsuarioRepository;
import com.tghtechnology.posweb.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Override
    public List<UsuarioDto> obtenerUsuarios(){
        
        List<Usuario> usuarios =  usuarioRepository.findAll();
        return usuarios.stream()
                    .map(UsuarioMapper::toDTO)
                    .collect(Collectors.toList());

    }

    @Override
    public boolean existeUsuario (Long id){
        return usuarioRepository.existsById(id);
    }

    @Override
    public void ingresarUsuario(Usuario user) {
        if (user == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }
        if (usuarioRepository.findByCorreo(user.getCorreo()).isPresent()) {
            throw new IllegalStateException("Ya existe un usuario con el correo proporcionado");
        }
        usuarioRepository.save(user);
    }



    @Override
    public void actualizarUsuario(Usuario user){
        if (usuarioRepository.existsById(user.getIdUsuario())) {
            usuarioRepository.save(user);
            System.out.println("Se actualizo el usuario");
        }else{
            System.out.println("El usuario no existe");
        }
    }

    @Override
    public UsuarioDto obtenerUsuarioId(Long id){
        if (usuarioRepository.existsById(id)) {
            Usuario usuario = usuarioRepository.findById(id).orElse(null);
            
            return UsuarioMapper.toDTO(usuario);
        }else{return null;}
    }

    @Override
    public void eliminarUsuario(Long id){
        if(usuarioRepository.existsById(id)){
            usuarioRepository.deleteById(id);
        }
    }

    @Override
    public void cambiarEstadoUsuario(Long id, String estado) {
        if (usuarioRepository.existsById(id)) {  
            Optional<Usuario> optionalUser = usuarioRepository.findById(id);

            if (optionalUser.isPresent()) {
                Usuario user = optionalUser.get();


                if (user.getEstado() == EstadoUsuario.ACTIVO) {
                    user.setEstado(EstadoUsuario.INACTIVO);
                } else if (user.getEstado() == EstadoUsuario.INACTIVO) {
                    user.setEstado(EstadoUsuario.ACTIVO);
                }
                usuarioRepository.save(user);
            }
        }
    }
    


    @Override
    public void cambiarConstrasena(Long id, String pass){
        Optional<Usuario> useroptiona = usuarioRepository.findById(id);

        if(useroptiona.isPresent()){
            Usuario user = useroptiona.get();
            user.setPass(pass);
            usuarioRepository.save(user);
        }
    }

    @Override
    public Set<RolDto> rolesUsuario(Long id){
        if (!usuarioRepository.existsById(id)) {
            return null;
        }
        Set<Rol> roles = usuarioRepository.findRolesByUsuarioId(id);

        return roles.stream()
                        .map(RolMapper::toDto)
                        .collect(Collectors.toSet());
    }

    @Override
    public void agregarRol(Long idUsuario, Long id){
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Rol rol = rolRepository.findById(id).orElseThrow(()->new RuntimeException("Rol no encontrado"));

        usuario.getRoles().add(rol);
        usuarioRepository.save(usuario);

    }
    
    @Override
    public void eliminarRol(Long idUsuario, Long idRol){
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Rol rol = rolRepository.findById(idRol).orElseThrow(()->new RuntimeException("Rol no encontrado"));

        usuario.getRoles().remove(rol);
        usuarioRepository.save(usuario);
    }

}
