package com.tghtechnology.posweb.data.repository;


import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tghtechnology.posweb.data.entities.Cliente;

@Repository
public interface  ClienteRepository extends JpaRepository<Cliente, Long> {

}
