package com.management.storage.controller;

import com.management.storage.exception.StorageManagementException;
import com.management.storage.model.ItemType;
import com.management.storage.services.ItemTypeService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/item-type")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ItemTypeController {

    private ItemTypeService itemTypeService;

    @GetMapping
    public List<ItemType> findAll() {
        return itemTypeService.findAll();
    }

    @GetMapping("{id}")
    public ItemType findById(@PathVariable final Long id) throws StorageManagementException {
        return itemTypeService.findById(id);
    }

    @PostMapping
    public ItemType create(@Valid @RequestBody final ItemType type) {
        return itemTypeService.createOrUpdate(type);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ItemType update(@Valid @RequestBody final ItemType type) {
        return itemTypeService.createOrUpdate(type);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable final Long id) {
        itemTypeService.deleteById(id);
    }
}
