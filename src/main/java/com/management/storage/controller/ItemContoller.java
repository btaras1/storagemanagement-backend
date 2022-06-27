package com.management.storage.controller;


import com.management.storage.model.Item;
import com.management.storage.services.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ItemContoller {

    ItemService itemService;

    @GetMapping
    List<Item> findAll() {
        return itemService.findAll();
    }

    @RequestMapping(value = "/type/{type:door|motor|guide|suspension}", method = RequestMethod.GET)
    List<Item> findAllByType(@PathVariable final String type) {
        return itemService.findAllByType(type.toUpperCase(Locale.ROOT));
    }

    @GetMapping("{id}")
    public Item findById(@PathVariable final Long id) {
        return itemService.findById(id);
    }

    @PostMapping
    public Item create(@Valid @RequestBody final Item item) {
        return itemService.createOrUpdate(item);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Item update(@Valid @RequestBody final Item item) {
        return itemService.createOrUpdate(item);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable final Long id) {
        itemService.deleteById(id);
    }

}
