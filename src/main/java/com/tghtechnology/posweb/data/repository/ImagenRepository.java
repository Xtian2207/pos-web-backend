package com.tghtechnology.posweb.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tghtechnology.posweb.data.entities.Imagen;

@Repository
public interface ImagenRepository extends JpaRepository<Imagen,Long>{

    
}
