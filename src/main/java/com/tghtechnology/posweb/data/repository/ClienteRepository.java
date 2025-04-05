package com.tghtechnology.posweb.data.repository;


import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tghtechnology.posweb.data.entities.Cliente;

@Repository
public interface  ClienteRepository extends JpaRepository<Cliente, Long> {
    @Query("SELECT c FROM Cliente c WHERE c.document = :documento")
    Optional<Cliente> obtenerClienteByDocu(@Param("documento") String documento);

    Optional<Cliente> findByDocument(String document);
}
