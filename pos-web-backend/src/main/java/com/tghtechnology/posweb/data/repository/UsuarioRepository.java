package com.tghtechnology.posweb.data.repository;

import com.tghtechnology.posweb.data.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import com.tghtechnology.posweb.data.entities.Rol;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Método para obtener todos los roles de un usuario
    @Query("select u.roles from Usuario u where u.idUsuario = :idUsuario")
    Set<Rol> findRolesByUsuarioId(@Param("idUsuario") Long idUsuario);
    
    Optional<Usuario> findByCorreo(String correo);

    Boolean existsByCorreo(String correo);
}
