package com.management.storage.controller;


import com.management.storage.dto.request.AddRequest;
import com.management.storage.dto.request.TransferRequest;
import com.management.storage.dto.response.*;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    List<StorageItemResponse> findAll(){
        List<Storage> storages = storageRepository.findAll();
        List<FullDetailItemsInStorage> detailItemsInStorages = storageRepository.test();
        List<StorageItemResponse> fullDetails = new ArrayList<>();

        for (Storage storage : storages){
            StorageItemResponse storageResponse = new StorageItemResponse();
            storageResponse.setId(storage.getId());
            storageResponse.setName(storage.getName());
            storageResponse.setLocation(storage.getLocation());
            storageResponse.setCreated(storage.getCreated());
            storageResponse.setModified(storage.getModified());

            Set<ItemStorageResponse> itemStorageResponses = new HashSet<>();
            for(FullDetailItemsInStorage detailItem : detailItemsInStorages){
                if(storage.getId() == detailItem.getStorageid()){
                    ItemStorageResponse itemStorageResponse = new ItemStorageResponse();
                    ItemStorageId itemStorageId = new ItemStorageId(detailItem.getItemid(), storage.getId());
                    ItemStorage currentItemStorage = itemStorageRepository.getById(itemStorageId);
                    itemStorageResponse.setId(currentItemStorage.getId());

                    Item item = itemRepository.getById(detailItem.getItemid());
                    itemStorageResponse.setItem(item);
                    itemStorageResponse.setValue(item.getValue());
                    itemStorageResponse.setDescription(item.getDescription());
                    if(item.getColor() != null) {
                        itemStorageResponse.setColor(item.getColor().getValue());
                    }
                    else {
                        itemStorageResponse.setColor(null);
                    }
                    itemStorageResponse.setGuideneeded(item.getGuide_needed());
                    itemStorageResponse.setStorage(storage);
                    if(detailItem.getQuantity() != null) {
                        itemStorageResponse.setQuantity(detailItem.getQuantity());
                    }
                    else{
                        itemStorageResponse.setQuantity(0);
                    }
                    if(detailItem.getMountedquantity() != null) {
                        itemStorageResponse.setNotMountedQuantity(detailItem.getMountedquantity());
                    }
                    else{
                        itemStorageResponse.setNotMountedQuantity(0);
                    }

                    Integer avaiableQuantity = itemStorageResponse.getQuantity() - itemStorageResponse.getNotMountedQuantity();
                    itemStorageResponse.setAvaliableQuantity(avaiableQuantity);

                    itemStorageResponses.add(itemStorageResponse);
                }

            }
            storageResponse.setItemStorages(itemStorageResponses);
            fullDetails.add(storageResponse);
        }
        return fullDetails;

    }

    @GetMapping("{id}")
    public Storage findById(@PathVariable Long id){return storageRepository.getById(id);}

    @RequestMapping(value = "/items-storage", method = RequestMethod.GET)
    public List<StorageItemsDetailResponse> findAllItems(){

        List<StorageItemsDetailResponse> storageItemsDetailResponses = new ArrayList<>();

        List<ItemsInStorageResponse> allItemsInStorage = storageRepository.allItemsInStorage();
        List<ItemsInStorageResponse> allItemsInStorageNotMounted = storageRepository.allItemsInStorageNotMounted();

        for (ItemsInStorageResponse itemAll : allItemsInStorage){
            Integer notMountedQuantity = 0;
            Integer avaliableQuantity = itemAll.getQuantity();

            for(ItemsInStorageResponse itemNotMounted : allItemsInStorageNotMounted){

                if(itemAll.getId() == itemNotMounted.getId()) {
                    notMountedQuantity = itemNotMounted.getQuantity();
                    avaliableQuantity = avaliableQuantity - notMountedQuantity;
                }

            }
            String desc = itemAll.getDescription();
            storageItemsDetailResponses.add(
                    new StorageItemsDetailResponse(itemAll.getId(), itemAll.getValue(), itemAll.getDescription(), itemAll.getType(), itemAll.getColor(), itemAll.getGuideneeded(), itemAll.getQuantity(), notMountedQuantity, avaliableQuantity )
            );

        }

        return storageItemsDetailResponses;
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
