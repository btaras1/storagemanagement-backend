package com.management.storage.controller;


import com.management.storage.model.Item;
import com.management.storage.repository.ItemRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    List<Item> findAll(){ return itemRepository.findAll();}

    @RequestMapping(value = "/type/{type:door|motor|guide}", method = RequestMethod.GET)
    List<Item> findAllByType(@PathVariable String type){return itemRepository.findAllByType(type.toUpperCase(Locale.ROOT));}

    @GetMapping("{id}")
    public Item findById(@PathVariable Long id){return itemRepository.getById(id);}

    @PostMapping
    public Item create(@RequestBody final Item item){return itemRepository.saveAndFlush(item);}

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Item update(@PathVariable Long id, @RequestBody Item item){
        Item currentItem = itemRepository.getById(id);
        BeanUtils.copyProperties(item, currentItem, "id");
        return itemRepository.saveAndFlush(currentItem);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
        itemRepository.deleteById(id);
    }
}
