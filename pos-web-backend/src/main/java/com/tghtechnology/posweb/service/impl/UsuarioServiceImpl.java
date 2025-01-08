package com.tghtechnology.posweb.service.impl;

import com.tghtechnology.posweb.data.dto.RolDto;
import com.tghtechnology.posweb.data.dto.UserCreateDTO;
import com.tghtechnology.posweb.data.dto.UsuarioDto;
import com.tghtechnology.posweb.data.entities.EstadoUsuario;
import com.tghtechnology.posweb.data.entities.Rol;
import com.tghtechnology.posweb.data.entities.Usuario;
import com.tghtechnology.posweb.data.mapper.RolMapper;
import com.tghtechnology.posweb.data.mapper.UsuarioMapper;
import com.tghtechnology.posweb.data.repository.RolRepository;
import com.tghtechnology.posweb.data.repository.UsuarioRepository;
import com.tghtechnology.posweb.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
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

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private RolMapper rolMapper;

    
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;


    @Override
    public List<UsuarioDto> obtenerUsuarios(){
        
        List<Usuario> usuarios =  usuarioRepository.findAll();
        return usuarios.stream()
                    .map(usuarioMapper::toDto)
                    .collect(Collectors.toList());

    }

    @Override
    public boolean existeUsuario (Long id){
        return usuarioRepository.existsById(id);
    }

    @Override
    public void ingresarUsuario(UserCreateDTO userCtr) {
        if (userCtr == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }
        userCtr.setPass(passwordEncoder.encode(userCtr.getPass()));
        Usuario user = usuarioMapper.toEntityCreate(userCtr);


        usuarioRepository.save(user);
    }



    @Override
    public void actualizarUsuario(UsuarioDto userD) {
        if (userD == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }

        Usuario user = usuarioMapper.toEntity(userD);
        if (existeUsuario(user.getIdUsuario())) {
            usuarioRepository.save(user);
        }
    }

    @Override
    public UsuarioDto obtenerUsuarioId(Long id){
        if (usuarioRepository.existsById(id)) {
            Usuario usuario = usuarioRepository.findById(id).orElse(null);
            
            return usuarioMapper.toDto(usuario);
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
    public void cambiarConstrasena(Long id, String pass) {
        Optional<Usuario> userOptional = usuarioRepository.findById(id);

        if (userOptional.isPresent()) {
            Usuario user = userOptional.get();

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
                        .map(rolMapper::toDto)
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

    @Override
    public UserDetails obtenerUserCorreo(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + correo));
        
        Set<GrantedAuthority> authorities = usuario.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getNombreRol())) // Usar el nombre del rol
            .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
            usuario.getCorreo(),
            usuario.getPass(),
            authorities
        );
    }



}
