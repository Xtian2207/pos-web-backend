package com.tghtechnology.posweb.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
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
public Map<String, String> generarYSubirCodigoBarras() throws IOException {
    // Generar un número aleatorio largo
    String codigoBarras = String.valueOf(100000000000L + new Random().nextLong(900000000000L));

    // Creamos el código de barras
    Code128Bean barcode128Bean = new Code128Bean();
    barcode128Bean.setHeight(15); // Altura del código de barras
    barcode128Bean.setModuleWidth(0.3); // Ancho de módulos en el código de barras
    barcode128Bean.setQuietZone(10); // Margen en blanco
    barcode128Bean.doQuietZone(true); // Garantiza que exista espacio en los bordes del código

    // Flujo de salida en memoria para almacenar la imagen del código de barras en formato binario
    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    // Se usa un BitmapCanvasProvider para renderizar el código de barras en formato PNG
    BitmapCanvasProvider canvas = new BitmapCanvasProvider(
            baos, "image/png", 300, BufferedImage.TYPE_BYTE_BINARY, false, 0
    );

    // Generar el código de barras usando el valor generado
    barcode128Bean.generateBarcode(canvas, codigoBarras);
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

    // Devolver el valor del código de barras y la URL de la imagen en un Map
    Map<String, String> result = new HashMap<>();
    result.put("codigoBarras", codigoBarras);
    result.put("barcodeUrl", barcodeUrl);

    return result;
}

    @Override
    public byte[] generateBarcodePdf(String barcodeUrl, int copies) throws IOException, DocumentException {
        // Validar el número de copias
        if (copies <= 0) {
            throw new IllegalArgumentException("El número de copias debe ser mayor que 0.");
        }

        // Crear el documento PDF
        Document document = new Document();
        // Crea un flujo de salida en memoria para almacenar el PDF
        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        // Asocia el documento con el flujo de salida para escribir el PDF
        PdfWriter.getInstance(document, pdfOutputStream);
        // Abre el documento para comenzar a agregar contenido
        document.open();

        // Descargar la imagen desde Cloudinary usando URI
        URI uri = URI.create(barcodeUrl); // Crea un objeto URI a partir de la URL del código de barras.
        URL url = uri.toURL(); // Convertir URI a URL
        // Carga la imagen del código de barras desde la URL en un objeto Image de iText
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

    @Override
    public String leerCodigoBarrasDesdeImagen(String imageUrl) throws IOException {
        try {
            // Crear una URI a partir de la URL de la imagen
            URI uri = new URI(imageUrl);
            // Convertir la URI a URL
            URL url = uri.toURL();

            // Descargar la imagen desde la URL
            BufferedImage bufferedImage = ImageIO.read(url);

            // Convertir la imagen a un formato que ZXing pueda procesar
            BinaryBitmap binaryBitmap = new BinaryBitmap(
                    new HybridBinarizer(new BufferedImageLuminanceSource(bufferedImage)));

            // Leer el código de barras
            try {
                Result result = new MultiFormatReader().decode(binaryBitmap);
                return result.getText(); // Devuelve el valor del código de barras
            } catch (Exception e) {
                throw new IOException("No se pudo leer el código de barras: " + e.getMessage());
            }
        } catch (URISyntaxException e) {
            throw new IOException("URL de la imagen no válida: " + e.getMessage());
        }
    }

}
