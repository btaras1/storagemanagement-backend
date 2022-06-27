package com.management.storage.services;

import com.management.storage.model.*;
import com.management.storage.model.composite.ItemProcurementId;
import com.management.storage.model.composite.ItemStorageId;
import com.management.storage.pdf.service.PdfGenerateService;
import com.management.storage.repository.ProcurementRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ProcurementService {

    ProcurementRepository procurementRepository;

    ItemStorageService itemStorageService;

    ItemProcurementService itemProcurementService;

    PdfGenerateService pdfGenerateService;


    public List<Procurement> findAll() {
        return procurementRepository.findAllByOrderByIdDesc();
    }

    public Procurement findById(final Long id) {
        return procurementRepository.getById(id);
    }

    @Transactional
    public Procurement create(final Procurement procurement) {
        if (procurement.getItemProcurements() != null) {
            Procurement currentProcurement = procurementRepository.save(procurement);
            currentProcurement.setItemProcurements(null);
            currentProcurement.setItemProcurements(mapItemsToProcurement(currentProcurement));
        }
        return procurementRepository.save(procurement);
    }

    @Transactional
    public Procurement update(final Procurement procurement) {
        //Distinct logika
        if (procurement.getItemProcurements() != null) {
            Procurement currentProcurement = procurementRepository.save(procurement);
            currentProcurement.setItemProcurements(null);
            currentProcurement.setItemProcurements(mapItemsToProcurement(currentProcurement));
        }
        return procurementRepository.save(procurement);
    }


    public ResponseEntity<?> downloadFromDB(final Long id) throws IOException {
        Procurement procurement = procurementRepository.getById(id);
        String fileName = generateFileName(procurement);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(prepareFileContent(fileName, procurement));
    }

    public void deleteById(final Long id) {
        procurementRepository.deleteById(id);
    }

    private List<ItemProcurement> mapItemsToProcurement(Procurement procurement) {
        return procurement.getItemProcurements().stream().map(item -> {
            Item currentItem = item.getItem();
            Storage currentStorage = procurement.getStorage();

            ItemStorage itemStorage = itemStorageService.findById(
                    ItemStorageId.builder()
                            .itemId(currentItem.getId())
                            .storageId(currentStorage.getId())
                            .build()
            );

            if (Objects.isNull(itemStorage)) {
                itemStorage = itemStorageService.createOrUpdate(
                        ItemStorage.builder()
                                .id(ItemStorageId.builder()
                                        .itemId(currentItem.getId())
                                        .storageId(currentStorage.getId())
                                        .build())
                                .item(currentItem)
                                .storage(currentStorage)
                                .quantity(item.getQuantity())
                                .build()
                );
            } else {
                itemStorage.setQuantity(item.getQuantity() + itemStorage.getQuantity());
            }
            if (Objects.nonNull(itemStorage)) {
                itemStorageService.createOrUpdate(itemStorage);
            }
            return itemProcurementService.createOrUpdate(
                    ItemProcurement.builder()
                            .id(ItemProcurementId.builder()
                                    .itemId(currentItem.getId())
                                    .procurementId(procurement.getId())
                                    .build())
                            .item(currentItem)
                            .procurement(procurement)
                            .quantity(item.getQuantity())
                            .build()
            );
        }).collect(Collectors.toList());
    }

    private byte[] prepareFileContent(String fileName, Procurement procurement) throws IOException {
        Map<String, Object> data = new HashMap<>();

        data.put("storage", procurement.getStorage());
        data.put("procurement", procurement);
        data.put("itemProcurements", procurement.getItemProcurements());
        File document = pdfGenerateService.generatePdfFile("procurement", data, fileName);

        return Files.readAllBytes(document.toPath());

    }

    private String generateFileName(Procurement procurement) {
        return "NABAVA_" + procurement.getCreated().toString() + "_" + procurement.getStorage().getName() + "_" + procurement.getStorage().getLocation() + "_" + UUID.randomUUID() + ".pdf";
    }

}
