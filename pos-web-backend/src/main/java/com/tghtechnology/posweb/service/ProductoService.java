package com.tghtechnology.posweb.service;

import com.tghtechnology.posweb.data.entities.Producto;
import com.tghtechnology.posweb.data.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> getAllproducts(){
        return productoRepository.findAll();
    }


}
