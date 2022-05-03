package com.management.storage.controller;

import com.management.storage.model.Item;
import com.management.storage.model.ItemType;
import com.management.storage.repository.ItemTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/item-type")
public class ItemTypeController {

    @Autowired
    private ItemTypeRepository itemTypeRepository;

    @GetMapping
    List<ItemType> findAll(){ return itemTypeRepository.findAll();}
}
