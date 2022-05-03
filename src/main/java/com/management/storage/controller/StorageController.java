package com.management.storage.controller;


import com.management.storage.dto.request.AddRequest;
import com.management.storage.dto.request.TransferRequest;
import com.management.storage.dto.response.ItemsInStorageResponse;
import com.management.storage.dto.response.StorageItemsResponse;
import com.management.storage.exception.SuppressableStacktraceException;
import com.management.storage.model.Item;
import com.management.storage.model.ItemStorage;
import com.management.storage.model.Storage;
import com.management.storage.model.composite.ItemStorageId;
import com.management.storage.repository.ItemRepository;
import com.management.storage.repository.ItemStorageRepository;
import com.management.storage.repository.StorageRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/storage")
public class StorageController {
    @Autowired
    private StorageRepository storageRepository;

    @Autowired
    private ItemStorageRepository itemStorageRepository;

    @Autowired
    private ItemRepository itemRepository;
    @GetMapping
    List<Storage> findAll(){return storageRepository.findAll();}

    @GetMapping("{id}")
    public Storage findById(@PathVariable Long id){return storageRepository.getById(id);}

    @RequestMapping(value = "/items-storage", method = RequestMethod.GET)
    public List<ItemsInStorageResponse> findAllItems(){
        return storageRepository.allItemsInStorage();

    }

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

    @PostMapping("/transfer")
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

    @PostMapping("/add")
    public void addItemToStorage(@RequestBody AddRequest addRequest) {
            if(itemStorageRepository.existsById(new ItemStorageId(addRequest.getItemId(), addRequest.getStorageId()))){
            ItemStorage itemStorage = itemStorageRepository.getById(new ItemStorageId(addRequest.getItemId(), addRequest.getStorageId()));
            itemStorage.setQuantity(itemStorage.getQuantity() + addRequest.getQuantity());
            itemStorageRepository.saveAndFlush(itemStorage);
        }
        else{
            ItemStorage newItemStorage = new ItemStorage(new ItemStorageId(addRequest.getItemId(), addRequest.getStorageId()), itemRepository.getById(addRequest.getItemId()),storageRepository.getById(addRequest.getStorageId()),addRequest.getQuantity());
            ItemStorage newItem = itemStorageRepository.saveAndFlush(newItemStorage);
        }
    }

    @GetMapping("/count-items")
    public Integer countItems() {
        return storageRepository.sumAllItemsInStorage();
    }


}
