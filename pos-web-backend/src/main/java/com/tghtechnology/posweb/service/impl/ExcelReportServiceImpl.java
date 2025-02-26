package com.tghtechnology.posweb.service.impl;

import java.util.List;

import com.tghtechnology.posweb.data.dto.VentaDTO;
import com.tghtechnology.posweb.service.ExcelReportService;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Service
public class ExcelReportServiceImpl implements ExcelReportService {

    @Override
    public byte[] generateSalesReport(List<VentaDTO> ventas) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Ventas");

            // Crear la fila de encabezados
            Row headerRow = sheet.createRow(0);
            String[] columnas = { "ID Venta", "Usuario", "Fecha Venta", "Hora Venta", "Método de Pago", "Tipo Venta",
                    "Total" };

            for (int i = 0; i < columnas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnas[i]);
            }

            // Formateador para la hora
            DateTimeFormatter horaFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

            // Llenar las filas con los datos de las ventas
            int rowNum = 1;
            for (VentaDTO venta : ventas) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(venta.getIdVenta());
                row.createCell(1).setCellValue(venta.getNombreUsuario());
                row.createCell(2).setCellValue(venta.getFechaVenta().toString()); // Fecha en formato yyyy-MM-dd
                row.createCell(3).setCellValue(venta.getHoraVenta().format(horaFormatter)); // Hora en formato HH:mm:ss
                row.createCell(4).setCellValue(venta.getMetodoPago());
                row.createCell(5).setCellValue(venta.getTipoDeVenta());
                row.createCell(6).setCellValue(venta.getTotal());
            }

            // Ajustar el tamaño de las columnas
            for (int i = 0; i < columnas.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Escribir el archivo Excel en un ByteArrayOutputStream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }

}
