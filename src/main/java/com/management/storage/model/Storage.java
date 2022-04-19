package com.management.storage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Storage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;

    @OneToMany(mappedBy = "storage")
    private Set<ItemStorage> itemStorages = new HashSet<>();

    @OneToMany(mappedBy="storage")
    @JsonIgnore
    private List<StorageArchive> storageArchives;

    @OneToMany(mappedBy="storage")
    @JsonIgnore
    private List<Product> products;

    @OneToMany(mappedBy="storage")
    @JsonIgnore
    private List<Procurement> procurements;

    public Storage() {
    }

    public Storage(String name, String location, Set<ItemStorage> itemStorages, List<StorageArchive> storageArchives) {
        this.name = name;
        this.location = location;
        this.itemStorages = itemStorages;
        this.storageArchives = storageArchives;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<ItemStorage> getItemStorages() {
        return itemStorages;
    }

    public void setItemStorages(Set<ItemStorage> itemStorages) {
        this.itemStorages = itemStorages;
    }

    public List<StorageArchive> getStorageArchives() {
        return storageArchives;
    }

    public void setStorageArchives(List<StorageArchive> storageArchives) {
        this.storageArchives = storageArchives;
    }
}
