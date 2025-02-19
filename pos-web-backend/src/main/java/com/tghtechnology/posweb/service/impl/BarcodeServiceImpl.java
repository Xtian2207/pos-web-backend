package com.tghtechnology.posweb.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.awt.image.BufferedImage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

import com.tghtechnology.posweb.service.BarcodeService;
import com.tghtechnology.posweb.service.CloudinaryService;

@Service
public class BarcodeServiceImpl implements BarcodeService {

    @Autowired
    private CloudinaryService cloudinaryService;

    @Override
    public String generateAndUploadBarcode() throws IOException {
        // Generación de un número aleatorio largo
        String barcodeText = String.valueOf(100000000000L + new Random().nextLong(900000000000L));

        // Creamos el código de barras
        Code128Bean barcode128Bean = new Code128Bean();
        barcode128Bean.setHeight(15);
        barcode128Bean.setModuleWidth(0.3); //Ancho de módulos en el código de barras
        barcode128Bean.setQuietZone(10); //Margen en Blanco
        barcode128Bean.doQuietZone(true); //Garantiza que exista espacio en los bordes del código

        //Flujo de salida en memoria para almacenar la imagen del código de barras en formato binario
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //Se usa un BitmapCanvasProvider para renderizar el código de barras en formato PNG
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(baos, "image/png", 300, BufferedImage.TYPE_BYTE_BINARY,
                false, 0);
        barcode128Bean.generateBarcode(canvas, barcodeText);
        canvas.finish();

        // Guardar la imagen temporalmente
        File file = File.createTempFile("barcode", ".png");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(baos.toByteArray());
        }

        // Subir la imagen usando CloudinaryService
        String barcodeUrl = (String) cloudinaryService.upload(file).get("url");
        // Eliminar el archivo temporal
        file.delete();

        return barcodeUrl;
    }

}
