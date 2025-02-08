package com.tghtechnology.posweb.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.tghtechnology.posweb.data.entities.Imagen;

public interface ImagenService {

    Imagen uploadImage(MultipartFile file) throws IOException;

    void deleteImage(Imagen imagen) throws IOException;
}
