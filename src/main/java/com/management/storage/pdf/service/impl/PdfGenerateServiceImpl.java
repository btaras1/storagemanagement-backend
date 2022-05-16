package com.management.storage.pdf.service.impl;

import com.lowagie.text.DocumentException;
import com.management.storage.pdf.service.PdfGenerateService;
import com.sun.javafx.PlatformUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.Map;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

@Service
public class PdfGenerateServiceImpl implements PdfGenerateService {
    private Logger logger = LoggerFactory.getLogger(PdfGenerateServiceImpl.class);

    @Autowired
    private TemplateEngine templateEngine;


    private String pdfDirectory;

    public static String getDefaultDownloadFolder() {
        try {
            if (PlatformUtil.isWindows()) {
                return Advapi32Util.registryGetStringValue(WinReg.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\User Shell Folders", "{374DE290-123F-4565-9164-39C4925E467B}");
            } else {
                return Paths.get(System.getProperty("user.home"), "Downloads").toString();
            }
        } catch (Exception ex) {
            LOGGER.error("Problem when reading in registry", ex);
        }
        return null;
    }

    @Override
    public File generatePdfFile(String templateName, Map<String, Object> data, String pdfFileName) {
        pdfDirectory = getDefaultDownloadFolder();
        Paths.get(System.getProperty("user.home"), "Downloads");
        Context context = new Context();
        context.setVariables(data);

        String htmlContent = templateEngine.process(templateName, context);
        try {
            File newPdf = new File(pdfDirectory + pdfFileName);
            FileOutputStream fileOutputStream = new FileOutputStream(newPdf);
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(fileOutputStream, false);
            renderer.finishPDF();
            return newPdf;
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        } catch (DocumentException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
