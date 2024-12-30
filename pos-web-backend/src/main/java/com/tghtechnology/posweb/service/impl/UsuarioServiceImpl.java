package com.tghtechnology.posweb.service.impl;

import com.tghtechnology.posweb.data.entities.EstadoUsuario;
import com.tghtechnology.posweb.data.entities.Rol;
import com.tghtechnology.posweb.data.entities.Usuario;
import com.tghtechnology.posweb.data.repository.RolRepository;
import com.tghtechnology.posweb.data.repository.UsuarioRepository;
import com.tghtechnology.posweb.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Override
    public List<Usuario> obtenerUsuarios(){
        return usuarioRepository.findAll();
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
    public Usuario obtenerUsuarioId(Long id){
        if (usuarioRepository.existsById(id)) {
            Optional<Usuario> usuario = usuarioRepository.findById(id);
            return usuario.orElse(null);
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
    public Set<Rol> rolesUsuario(Long id){
        if (!usuarioRepository.existsById(id)) {
            return null;
        }
        return usuarioRepository.findRolesByUsuarioId(id);
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
