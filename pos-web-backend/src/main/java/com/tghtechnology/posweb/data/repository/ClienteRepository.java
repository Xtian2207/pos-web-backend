package com.tghtechnology.posweb.data.repository;


import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tghtechnology.posweb.data.entities.Cliente;

@Repository
public interface  ClienteRepository extends JpaRepository<Cliente, Long> {
    @Query("select c from Cliente c where document =: documento")
    Cliente obtenerClienteByDocu(@Param("documento") String documento);

    Boolean findByDocument(String doc);
}
