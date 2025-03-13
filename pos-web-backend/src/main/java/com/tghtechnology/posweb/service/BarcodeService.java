package com.tghtechnology.posweb.service;

import java.io.IOException;

import com.itextpdf.text.DocumentException;

public interface BarcodeService {

    String generateAndUploadBarcode() throws IOException;

    byte[] generateBarcodePdf(String barcodeText, int copies) throws IOException, DocumentException;

}
