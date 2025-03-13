package com.tghtechnology.posweb.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Random;
import java.awt.image.BufferedImage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
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
        barcode128Bean.setModuleWidth(0.3); // Ancho de módulos en el código de barras
        barcode128Bean.setQuietZone(10); // Margen en Blanco
        barcode128Bean.doQuietZone(true); // Garantiza que exista espacio en los bordes del código

        // Flujo de salida en memoria para almacenar la imagen del código de barras en
        // formato binario
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // Se usa un BitmapCanvasProvider para renderizar el código de barras en formato
        // PNG
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

    @Override
    public byte[] generateBarcodePdf(String barcodeUrl, int copies) throws IOException, DocumentException {
        // Validar el número de copias
        if (copies <= 0) {
            throw new IllegalArgumentException("El número de copias debe ser mayor que 0.");
        }

        // Crear el documento PDF
        Document document = new Document();
        //Crea un flujo de salida en memoria para almacenar el PDF
        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        //Asocia el documento con el flujo de salida para escribir el PDF
        PdfWriter.getInstance(document, pdfOutputStream);
        //Abre el documento para comenzar a agregar contenido
        document.open();

        // Descargar la imagen desde Cloduinary usando URI
        URI uri = URI.create(barcodeUrl); // Crea un objeto URI a partir de la URL del código de barras.
        URL url = uri.toURL(); // Convertir URI a URL
        //Carga la imagen del código de barras desde la URL en un objeto Image de iText
        Image barcodeImage = Image.getInstance(url);

        // Ajustar el tamaño de la imagen
        barcodeImage.scaleToFit(200, 100); 
        
        // Agregar las copias al PDF (una por fila)
        for (int i = 0; i < copies; i++) {
            // Centrar la imagen en la página
            barcodeImage.setAlignment(Image.ALIGN_CENTER);
            document.add(barcodeImage);

            // Agregar más espacio entre las imágenes
            if (i < copies - 1) {
                document.add(new Paragraph(" ")); // Espacio adicional
                document.add(Chunk.NEWLINE); // Salto de línea
                document.add(new Paragraph(" ")); // Más espacio
            }
        }

        document.close();
        return pdfOutputStream.toByteArray();
    }
}
