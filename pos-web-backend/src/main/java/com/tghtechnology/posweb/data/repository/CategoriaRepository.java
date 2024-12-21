package com.tghtechnology.posweb.data.repository;

import com.tghtechnology.posweb.data.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Categoria findByIdCategoria(Long id);
}
