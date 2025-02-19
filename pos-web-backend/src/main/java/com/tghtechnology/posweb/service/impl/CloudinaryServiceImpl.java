package com.tghtechnology.posweb.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tghtechnology.posweb.service.CloudinaryService;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private Cloudinary cloudinary;

    public CloudinaryServiceImpl() {
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("cloud_name", "dmluij99q");
        valuesMap.put("api_key", "499152864116465");
        valuesMap.put("api_secret", "8YtqGisrqrWIzqrl7ZlGsRxTx6E");
        cloudinary = new Cloudinary(valuesMap);
    }

    /*
     * @Override
     * public Map<?, ?> upload(MultipartFile multipartFile) throws IOException {
     * File file = convert(multipartFile); //Convertimos el archivo MultipartFile en
     * un File local
     * //Subimos el archivo a Cloudinary
     * Map<?,?> result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
     * if(!Files.deleteIfExists(file.toPath())){
     * throw new IOException("Error al eliminar foto: " + file.getAbsolutePath());
     * }
     * return result;
     * 
     * }
     */

    @Override
    public Map<?, ?> upload(MultipartFile multipartFile) throws IOException {
        File file = convert(multipartFile);

        Map<String, Object> result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());

        Files.deleteIfExists(file.toPath());

        String url = (String) result.get("secure_url");
        String publicId = (String) result.get("public_id");

        Map<String, Object> response = new HashMap<>();
        response.put("url", url);
        response.put("public_id", publicId);
        return response;
    }

    @Override
    public Map<?, ?> delete(String id) throws IOException {
        Map<?, ?> result = cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
        return result;
    }

    private File convert(MultipartFile multipartFile) throws IOException {
        // Creamos un archivo temporal con el mismo nombre del archivo original
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        // Abrimos un FileOutputStream para escribir en el archivo
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(multipartFile.getBytes());
        fo.close();
        return file;
    }

    @Override
    public Map<?, ?> upload(File file) throws IOException {
        Map<String, Object> result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        Files.deleteIfExists(file.toPath()); // Eliminamos el archivo después de subirlo

        String url = (String) result.get("secure_url");
        String publicId = (String) result.get("public_id");

        Map<String, Object> response = new HashMap<>();
        response.put("url", url);
        response.put("public_id", publicId);
        return response;
    }
}
