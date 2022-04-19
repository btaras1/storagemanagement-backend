package com.management.storage.controller;


import com.management.storage.model.Item;
import com.management.storage.model.ItemProcurement;
import com.management.storage.model.ItemStorage;
import com.management.storage.model.Procurement;
import com.management.storage.model.composite.ItemStorageId;
import com.management.storage.repository.ItemStorageRepository;
import com.management.storage.repository.ProcurementRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/procurement")
public class ProcurementController {
    @Autowired
    private ProcurementRepository procurementRepository;

    @Autowired
    private ItemStorageRepository itemStorageRepository;

    @GetMapping
    List<Procurement> findAll(){ return procurementRepository.findAll();}

    @GetMapping("{id}")
    public Procurement findById(@PathVariable Long id){return procurementRepository.getById(id);}

    @PostMapping
    public Procurement create(@RequestBody final Procurement procurement){
            Set<ItemProcurement> itemsOnProcurement = procurement.getItemProcurements();
            List<Item> items = new ArrayList<>();
        for (ItemProcurement item: itemsOnProcurement) {
            ItemStorage itemStorage = itemStorageRepository.getById(new ItemStorageId(item.getItem().getId(), procurement.getStorage().getId()));
            itemStorage.setQuantity(item.getQuantity() + item.getQuantity());
            itemStorageRepository.saveAndFlush(itemStorage);
        }
            return procurementRepository.saveAndFlush(procurement);
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
