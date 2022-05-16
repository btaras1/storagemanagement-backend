package com.management.storage.pdf.service;

import java.io.File;
import java.util.Map;

public interface PdfGenerateService {
    File generatePdfFile(String templateName, Map<String, Object> data, String pdfFileName);
}
