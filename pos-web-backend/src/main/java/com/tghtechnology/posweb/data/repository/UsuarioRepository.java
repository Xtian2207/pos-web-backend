package com.tghtechnology.posweb.data.repository;

import com.tghtechnology.posweb.data.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Obtener usuarios con idRol
    @Query("select u from Usuario u where u.rol.idRol = :idRol")
    List<Usuario> findUsuarioByRol(@Param("idRol") Long idRol);

    // Obtener usuarios con el nombre del rol
    @Query("select u from Usuario u where u.rol.nombreRol = :nombredRol")
    List<Usuario> findUsuarioByNombreRol(@Param("idRol") String nombreRol);

}
