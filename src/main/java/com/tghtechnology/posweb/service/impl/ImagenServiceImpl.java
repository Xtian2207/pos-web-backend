package com.tghtechnology.posweb.service.impl;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tghtechnology.posweb.data.entities.Imagen;
import com.tghtechnology.posweb.data.repository.ImagenRepository;
import com.tghtechnology.posweb.service.CloudinaryService;
import com.tghtechnology.posweb.service.ImagenService;

@Service
public class ImagenServiceImpl implements ImagenService{

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ImagenRepository imagenRepository;

    @Override
    public Imagen uploadImage(MultipartFile file) throws IOException {
        Map<?,?> uploadResult = cloudinaryService.upload(file);
        String imageUrl = (String) uploadResult.get("url");
        String imageId = (String) uploadResult.get("public_id");
        Imagen imagen = new Imagen(file.getOriginalFilename(),imageUrl,imageId);
        return imagenRepository.save(imagen);
    }

    @Override
    public void deleteImage(Imagen imagen) throws IOException {
        cloudinaryService.delete(imagen.getImagenId());
        imagenRepository.deleteById(imagen.getId());
    }
    
}
