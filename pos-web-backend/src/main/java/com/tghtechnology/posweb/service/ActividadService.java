package com.tghtechnology.posweb.service;

import com.tghtechnology.posweb.data.entities.ActividadEmpleado;
import com.tghtechnology.posweb.data.repository.ActividadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActividadService {

    @Autowired
    private ActividadRepository actividadRepository;

}
