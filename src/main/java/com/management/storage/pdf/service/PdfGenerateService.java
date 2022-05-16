package com.management.storage.pdf.service;

import java.io.File;
import java.util.Map;

public interface PdfGenerateService {
    void generatePdfFile(String templateName, Map<String, Object> data, String pdfFileName);
}
