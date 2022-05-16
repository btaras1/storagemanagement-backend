package com.management.storage.controller;


import com.management.storage.model.*;
import com.management.storage.model.composite.ItemProcurementId;
import com.management.storage.model.composite.ItemStorageId;
import com.management.storage.pdf.service.PdfGenerateService;
import com.management.storage.repository.ItemProcurementRepository;
import com.management.storage.repository.ItemStorageRepository;
import com.management.storage.repository.ProcurementRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/procurement")
public class ProcurementController {
    @Autowired
    private ProcurementRepository procurementRepository;

    @Autowired
    private ItemStorageRepository itemStorageRepository;

    @Autowired
    private ItemProcurementRepository itemProcurementRepository;

    @Autowired
    private PdfGenerateService pdfGenerateService;

    @GetMapping
    List<Procurement> findAll(){ return procurementRepository.findAllByOrderByIdDesc();}

    @GetMapping("{id}")
    public Procurement findById(@PathVariable Long id){return procurementRepository.getById(id);}

    @PostMapping
    public Procurement create(@RequestBody final Procurement procurement){
            List<ItemProcurement> itemsOnProcurement = procurement.getItemProcurements();
            List<ItemProcurement> mappedItemsOnProcurement = new ArrayList<>();
            Procurement currentProcurement = procurementRepository.saveAndFlush(procurement);

        for (ItemProcurement item: itemsOnProcurement) {
            Item currentItem = item.getItem();
            Storage currentStorage = procurement.getStorage();

            ItemStorageId itemStorageId = new ItemStorageId();
            itemStorageId.setItemId(currentItem.getId());
            itemStorageId.setStorageId(currentStorage.getId());

            ItemStorage itemStorage = (itemStorageRepository.findById(itemStorageId)).stream().findFirst().orElse(null);

            if(itemStorage == null) {
                ItemStorageId newItemStorageId = new ItemStorageId(currentItem.getId(), currentStorage.getId());
                ItemStorage newItemStorage = new ItemStorage(newItemStorageId,currentItem, currentStorage, item.getQuantity());
                itemStorageRepository.saveAndFlush(newItemStorage);
                itemStorage = (itemStorageRepository.findById(newItemStorageId)).stream().findFirst().orElse(null);
            }
            else {
                itemStorage.setQuantity(item.getQuantity() + itemStorage.getQuantity());
            }
            itemStorageRepository.saveAndFlush(itemStorage);
            ItemProcurementId currentItemProcurementId = new ItemProcurementId(currentItem.getId(), currentProcurement.getId());
            ItemProcurement itemProcurement = itemProcurementRepository.saveAndFlush(new ItemProcurement(currentItemProcurementId, currentItem, currentProcurement, item.getQuantity()));
            mappedItemsOnProcurement.add(itemProcurement);
        }
            currentProcurement.setItemProcurements(null);
            currentProcurement.setItemProcurements(mappedItemsOnProcurement);
            return procurementRepository.saveAndFlush(currentProcurement);
    }

    @GetMapping("/pdf/{id}")
    public void getPdf(@PathVariable Long id) {
        Map<String, Object> data = new HashMap<>();
        Procurement procurement = procurementRepository.getById(id);
        data.put("storage", procurement.getStorage());
        data.put("procurement", procurement);
        data.put("itemProcurements", procurement.getItemProcurements());
        String fileName = "NABAVA_" + procurement.getCreated().toString() + "_" + procurement.getStorage().getName() + "_" + procurement.getStorage().getLocation() + "_" + UUID.randomUUID().toString() + ".pdf";
        pdfGenerateService.generatePdfFile("procurement", data, fileName);


    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Procurement update(@PathVariable Long id, @RequestBody Procurement procurement){
        Procurement currentProcurement = procurementRepository.getById(id);
        BeanUtils.copyProperties(procurement, currentProcurement, "id");
        return procurementRepository.saveAndFlush(currentProcurement);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
        procurementRepository.deleteById(id);
    }
}
