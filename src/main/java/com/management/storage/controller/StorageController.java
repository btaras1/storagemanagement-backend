package com.management.storage.controller;


import com.management.storage.dto.request.AddRequestDto;
import com.management.storage.dto.request.TransferRequestDto;
import com.management.storage.dto.response.ItemsCountResponseDto;
import com.management.storage.dto.response.StorageItemResponseDto;
import com.management.storage.dto.response.StorageItemsDetailResponseDto;
import com.management.storage.model.Storage;
import com.management.storage.services.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/storage")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class StorageController {

    StorageService storageService;

    @GetMapping
    public List<StorageItemResponseDto> findAll() {
        return storageService.findAll();
    }

    @GetMapping("{id}")
    public Storage findById(@PathVariable final Long id) {
        return storageService.findById(id);
    }

    @RequestMapping(value = "/items-storage", method = RequestMethod.GET)
    public List<StorageItemsDetailResponseDto> findAllItems() {
        return storageService.findAllItems();
    }

    @PostMapping
    public Storage create(@Valid @RequestBody final Storage storage) {
        return storageService.createOrUpdate(storage);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Storage update(@Valid @RequestBody final Storage storage) {
        return storageService.createOrUpdate(storage);
    }
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable final Long id) {
        storageService.deleteById(id);
    }

    @GetMapping("/count-items")
    public List<ItemsCountResponseDto> countItems() {
        return storageService.countItems();
    }


}
