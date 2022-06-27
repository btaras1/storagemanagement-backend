package com.management.storage.services;

import com.management.storage.dto.request.SetMountRequestDto;
import com.management.storage.dto.response.MostSelledDoor;
import com.management.storage.dto.response.MostSelledDoorResponseDto;
import com.management.storage.exception.ApiError;
import com.management.storage.exception.StorageManagementException;
import com.management.storage.model.*;
import com.management.storage.model.composite.ItemReceiptId;
import com.management.storage.model.composite.ItemStorageId;
import com.management.storage.pdf.service.PdfGenerateService;
import com.management.storage.repository.ReceiptRepository;
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
import java.time.LocalDate;
import java.util.*;

import static java.util.stream.Collectors.toSet;
import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ReceiptService {

    ReceiptRepository receiptRepository;


    ColorService colorService;


    BuyerService buyerService;


    ItemStorageService itemStorageService;


    ItemReceiptService itemReceiptService;

    PdfGenerateService pdfGenerateService;

    public List<Receipt> findAll() {
        return receiptRepository.findAll();
    }

    public Receipt findById(final Long id) throws StorageManagementException {
        return receiptRepository.findById(id).orElseThrow(() -> new StorageManagementException(ApiError.RECEIPT_NOT_FOUND));
    }

    @Transactional
    public Receipt create(final Receipt receipt) {
        if (receipt.getBuyer().getId() == null) {
            Buyer buyer = buyerService.createOrUpdate(receipt.getBuyer());
            receipt.setBuyer(buyer);
        }
        Set<ItemReceipt> receiptItems = receipt.getItemReceipts();
        Receipt receiptWithoutItems = receipt;
        receiptWithoutItems.setItemReceipts(null);
        Receipt currentReceipt = receiptRepository.save(receiptWithoutItems);
        receipt.setItemReceipts(receiptItems);
        if (receiptItems != null & receiptItems.size() > 0) {
            currentReceipt.setItemReceipts(null);
            currentReceipt.setItemReceipts(mapItemsToReceipt(receipt, receiptItems));
            return receiptRepository.save(currentReceipt);
        }
        return currentReceipt;
    }

    public Receipt update(Receipt receipt) {
        return receiptRepository.save(receipt);
    }

    public void deleteById(final Long id) {
        receiptRepository.deleteById(id);
    }

    public Receipt getLatest() {
        return receiptRepository.getLastReceipt();
    }

    public ResponseEntity<?> downloadFromDB(final Long id) throws IOException {
        Receipt receipt = receiptRepository.getById(id);
        String fileName = generateFileName(receipt);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(prepareFileContent(fileName, receipt));
    }

    public MostSelledDoorResponseDto getMostSoldDoor() {
        MostSelledDoor mostSelledDoor = receiptRepository.mostSoldDoor();
        String name = mostSelledDoor.getValue() + " - " +
                colorService.findById(mostSelledDoor.getColor_Id()).getValue();
        ;
        return new MostSelledDoorResponseDto(name, mostSelledDoor.getCount());

    }

    public Integer countReceiptsForCurrentMonth() {
        return receiptRepository.countReceiptsForCurrentMonth();
    }

    public Receipt setMounted(final Long id, final SetMountRequestDto setMountRequest) {
        Receipt currentReceipt = receiptRepository.getById(id);
        currentReceipt.setMounted(true);
        currentReceipt.setMountedDate(LocalDate.now());
        currentReceipt.setEmployees(setMountRequest.getEmployees());
        currentReceipt.setDescription(setMountRequest.getDescription());
        updateQuantityInStorage(currentReceipt.getItemReceipts());
        return receiptRepository.save(currentReceipt);
    }

    public List<Receipt> getAllNotMounted() {
        return receiptRepository.getReceiptNotMounted();
    }

    @Transactional
    Set<ItemReceipt> mapItemsToReceipt(Receipt receipt, Set<ItemReceipt> receiptItems) {
        if (receiptItems != null & receiptItems.size() > 0) {
            return receiptItems
                    .stream()
                    .map(itemReceipt -> {
                        Item item = itemReceipt.getItem();
                        Storage storage = itemReceipt.getStorage();

                        ItemReceiptId itemReceiptId = ItemReceiptId.builder()
                                .itemId(item.getId())
                                .storageId(storage.getId())
                                .receiptId(receipt.getId())
                                .build();

                        itemReceipt.setId(itemReceiptId);
                        itemReceipt.setReceipt(receipt);
                        return itemReceiptService.createOrUpdate(itemReceipt);
                    }).collect(toSet());
        } else return null;
    }

    private byte[] prepareFileContent(String fileName, Receipt receipt) throws IOException {
        Map<String, Object> data = new HashMap<>();

        data.put("buyer", receipt.getBuyer());
        data.put("receipt", receipt);
        data.put("itemReceipts", receipt.getItemReceipts());
        data.put("employees", receipt.getEmployees());
        File document = pdfGenerateService.generatePdfFile("receipt", data, fileName);

        return Files.readAllBytes(document.toPath());

    }

    private String generateFileName(Receipt receipt) {
        return "RACUN_" + receipt.getSold().toString() + "_" + receipt.getBuyer().getFirstname() + "_" + receipt.getBuyer().getLastname() + "_" + UUID.randomUUID() + ".pdf";
    }

    @Transactional
    void updateQuantityInStorage(Set<ItemReceipt> receiptItems) {
        receiptItems.stream().forEach(itemReceipt -> {

            ItemStorageId itemStorageId = ItemStorageId.builder()
                    .storageId(itemReceipt.getStorage().getId())
                    .itemId(itemReceipt.getItem().getId())
                    .build();


            ItemStorage itemStorage = itemStorageService.findById(itemStorageId);

            if (Objects.nonNull(itemStorage)) {
                itemStorage.setQuantity(itemStorage.getQuantity() - itemReceipt.getQuantity());
                itemStorageService.createOrUpdate(itemStorage);
            }
        });
    }
}
