package com.management.storage.controller;


import com.lowagie.text.DocumentException;
import com.management.storage.dto.request.SetMountRequest;
import com.management.storage.dto.response.MostSelledDoor;
import com.management.storage.dto.response.MostSelledDoorResponse;
import com.management.storage.model.*;
import com.management.storage.model.composite.ItemProcurementId;
import com.management.storage.model.composite.ItemReceiptId;
import com.management.storage.model.composite.ItemStorageId;
import com.management.storage.pdf.service.PdfGenerateService;
import com.management.storage.repository.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;



@RestController
@RequestMapping("/receipt")
public class ReceiptController {
    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private ItemStorageRepository itemStorageRepository;

    @Autowired
    private ItemReceiptRepository itemReceiptRepository;

    @Autowired
    private StorageRepository storageRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PdfGenerateService pdfGenerateService;

    @GetMapping
    List<Receipt> findAll(){return receiptRepository.findAll();}

    @GetMapping("{id}")
    public Receipt findById(@PathVariable Long id){return receiptRepository.getById(id);}

    @PostMapping
    public Receipt create(@RequestBody final Receipt receipt){
        if(receipt.getBuyer().getId() == null) {
            Buyer buyer = buyerRepository.saveAndFlush(receipt.getBuyer());
            receipt.setBuyer(buyer);
        }
        Receipt currentReceipt = receiptRepository.saveAndFlush(receipt);
        Set<ItemReceipt> itemReceipts = new HashSet<>();


        for(ItemReceipt itemReceipt: receipt.getItemReceipts()) {
            Item item = itemRepository.getById(itemReceipt.getItem().getId());
            Storage storage = storageRepository.getById(itemReceipt.getStorage().getId());

            ItemReceiptId itemReceiptId = new ItemReceiptId();
            itemReceiptId.setItemId(item.getId());
            itemReceiptId.setStorageId(storage.getId());
            itemReceiptId.setReceiptId(currentReceipt.getId());


            itemReceipt.setId(itemReceiptId);
            itemReceipt.setReceipt(currentReceipt);
            ItemReceipt newItemReceipt = itemReceiptRepository.saveAndFlush(itemReceipt);

            itemReceipts.add(newItemReceipt);


        }
        currentReceipt.setItemReceipts(null);
        currentReceipt.setMounted(false);
        currentReceipt.setItemReceipts(itemReceipts);
        return receiptRepository.saveAndFlush(currentReceipt);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Receipt update(@PathVariable Long id, @RequestBody Receipt receipt){
        Receipt currentReceipt = receiptRepository.getById(id);
        BeanUtils.copyProperties(receipt, currentReceipt, "id");
        return receiptRepository.saveAndFlush(currentReceipt);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
        receiptRepository.deleteById(id);
    }

    @GetMapping("/last")
    public Receipt getLastest() {
        return receiptRepository.getLastReceipt();
    }

    @GetMapping("/pdf/{id}")
    public void getPdf(@PathVariable Long id) {
        Map<String, Object> data = new HashMap<>();
        Receipt receipt = receiptRepository.getById(id);
        data.put("buyer", receipt.getBuyer());
        data.put("receipt", receipt);
        data.put("itemReceipts", receipt.getItemReceipts());
        data.put("employees", receipt.getEmployees());
        String fileName = "RACUN_" + receipt.getSold().toString() + "_" + receipt.getBuyer().getFirstname() + "_"+ receipt.getBuyer().getLastname() + "_" + UUID.randomUUID().toString() + ".pdf";
       pdfGenerateService.generatePdfFile("receipt", data, fileName);

    }

    @GetMapping("/download/{id}")
    public ResponseEntity downloadFromDB(@PathVariable Long id) throws IOException {
        Map<String, Object> data = new HashMap<>();
        Receipt receipt = receiptRepository.getById(id);
        data.put("buyer", receipt.getBuyer());
        data.put("receipt", receipt);
        data.put("itemReceipts", receipt.getItemReceipts());
        data.put("employees", receipt.getEmployees());
        String fileName = "RACUN_" + receipt.getSold().toString() + "_" + receipt.getBuyer().getFirstname() + "_"+ receipt.getBuyer().getLastname() + "_" + UUID.randomUUID().toString() + ".pdf";
        File document = pdfGenerateService.generatePdfFile("receipt", data, fileName);
        byte[] fileContent = Files.readAllBytes(document.toPath());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(fileContent);
    }

    @GetMapping("/most-selled-door")
    public MostSelledDoorResponse getMostSelledDoor() {
        MostSelledDoor mostSelledDoor = receiptRepository.mostSelledDoor();
        String doorColor = colorRepository.getById(mostSelledDoor.getColor_Id()).getValue();
        String name = mostSelledDoor.getValue() + " - " + doorColor;
        return new MostSelledDoorResponse(name, mostSelledDoor.getCount());

    }

    @GetMapping("/count-receipts-current-month")
    public Integer countReceiptsForCurrentMonth() {
        return receiptRepository.countReceiptsForCurrentMonth();
    }

    @RequestMapping(value = "/mount/{id}", method = RequestMethod.PUT)
    public Receipt setMounted(@PathVariable Long id, @RequestBody SetMountRequest setMountRequest){
        Receipt currentReceipt = receiptRepository.getById(id);
        currentReceipt.setMounted(true);
        currentReceipt.setMountedDate(new Date ());
        currentReceipt.setEmployees(setMountRequest.getEmployees());
        currentReceipt.setDescription(setMountRequest.getDescription());

        for (ItemReceipt itemReceipt : currentReceipt.getItemReceipts()){
            ItemStorageId itemStorageId = new ItemStorageId();
            itemStorageId.setItemId(itemReceipt.getItem().getId());
            itemStorageId.setStorageId(itemReceipt.getStorage().getId());

            ItemStorage itemStorage = (itemStorageRepository.findById(itemStorageId)).stream().findFirst().orElse(null);
            if(itemStorage != null) {
                itemStorage.setQuantity(itemStorage.getQuantity() - itemReceipt.getQuantity());
                itemStorageRepository.saveAndFlush(itemStorage);
            }
        }
        return receiptRepository.saveAndFlush(currentReceipt);
    }
    @RequestMapping(value = "/mount-false", method = RequestMethod.GET)
    public List<Receipt> getAllNotMounted(){
        return receiptRepository.getReceiptNotMounted();
    }
}
