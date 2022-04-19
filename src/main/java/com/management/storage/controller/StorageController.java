package com.management.storage.controller;


import com.management.storage.dto.request.TransferRequest;
import com.management.storage.model.ItemStorage;
import com.management.storage.model.Storage;
import com.management.storage.model.composite.ItemStorageId;
import com.management.storage.repository.ItemStorageRepository;
import com.management.storage.repository.StorageRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/storage")
public class StorageController {
    @Autowired
    private StorageRepository storageRepository;

    @Autowired
    private ItemStorageRepository itemStorageRepository;

    @GetMapping
    List<Storage> findAll(){return storageRepository.findAll();}

    @GetMapping("{id}")
    public Storage findById(@PathVariable Long id){return storageRepository.getById(id);}

    @PostMapping
    public Storage create(@RequestBody final Storage storage){return storageRepository.saveAndFlush(storage);}

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Storage update(@PathVariable Long id, @RequestBody Storage storage){
        Storage currentStorage = storageRepository.getById(id);
        BeanUtils.copyProperties(storage, currentStorage, "id");
        return storageRepository.saveAndFlush(currentStorage);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
        storageRepository.deleteById(id);
    }

    @RequestMapping(value = "/transfer/", method = RequestMethod.POST)
    public void transferItem(@RequestBody TransferRequest transferRequest){
        ItemStorage transferFrom = itemStorageRepository.getById(new ItemStorageId(transferRequest.getItemId(),transferRequest.getFromStorageId()));
        ItemStorage transferTo = itemStorageRepository.getById(new ItemStorageId(transferRequest.getItemId(),transferRequest.getToStorageId()));
        transferFrom.setQuantity(transferFrom.getQuantity() - transferRequest.getQuantity());
        transferTo.setQuantity(transferTo.getQuantity() + transferRequest.getQuantity());
        List<ItemStorage> updateData = new ArrayList<>();
        updateData.add(transferFrom);
        updateData.add(transferTo);
        itemStorageRepository.saveAllAndFlush(updateData);
    }
}
