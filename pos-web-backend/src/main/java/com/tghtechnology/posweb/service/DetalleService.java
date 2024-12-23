package com.tghtechnology.posweb.service;

import com.tghtechnology.posweb.data.entities.DetalleVenta;
import com.tghtechnology.posweb.data.repository.DetalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleService {

    @Autowired
    private DetalleRepository detalleRepository;

    public List<DetalleVenta> getAlldetallesByventa(Long idVenta){
        return detalleRepository.finByIdVenta(idVenta);
    }

}
