package com.tghtechnology.posweb.service;

import java.io.IOException;
import java.util.Map;

import com.itextpdf.text.DocumentException;


public interface BarcodeService {

    Map<String, String> generarYSubirCodigoBarras() throws IOException;
    String leerCodigoBarrasDesdeImagen(String imageUrl) throws IOException;
    byte[] generateBarcodePdf(String barcodeUrl, int copies) throws IOException, DocumentException;
}
