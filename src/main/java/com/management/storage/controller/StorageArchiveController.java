package com.management.storage.controller;

import com.management.storage.model.StorageArchive;
import com.management.storage.model.StorageArchive;
import com.management.storage.repository.StorageArchiveRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/archive")
public class StorageArchiveController {
    @Autowired
    private StorageArchiveRepository storageArchiveRepository;

    @GetMapping
    List<StorageArchive> findAll(){return storageArchiveRepository.findAll();}

    @GetMapping("{id}")
    public StorageArchive findById(@PathVariable Long id){return storageArchiveRepository.getById(id);}

    @PostMapping
    public StorageArchive create(@RequestBody final StorageArchive storageArchive){return storageArchiveRepository.saveAndFlush(storageArchive);}

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public StorageArchive update(@PathVariable Long id, @RequestBody StorageArchive storageArchive){
        StorageArchive currentStorageArchive = storageArchiveRepository.getById(id);
        BeanUtils.copyProperties(storageArchive, currentStorageArchive, "id");
        return storageArchiveRepository.saveAndFlush(currentStorageArchive);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
        storageArchiveRepository.deleteById(id);
    }
}
