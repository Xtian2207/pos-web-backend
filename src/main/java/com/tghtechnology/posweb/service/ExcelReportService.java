package com.tghtechnology.posweb.service;

import java.io.IOException;
import java.util.List;

import com.tghtechnology.posweb.data.dto.VentaDTO;

public interface ExcelReportService {

    byte[] generateSalesReport(List<VentaDTO> ventas) throws IOException;

}
