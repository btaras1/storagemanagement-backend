package com.management.storage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class StorageArchive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="storage_id", nullable=true)
    private Storage storage;

    @OneToMany(mappedBy = "storageArchive")
    private Set<ItemStorageArchive> itemStorageArchives = new HashSet<>();

    public StorageArchive() {
    }

    public StorageArchive(Date date, Storage storage) {
        this.date = date;
        this.storage = storage;
    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Set<ItemStorageArchive> getItemStorageArchives() {
        return itemStorageArchives;
    }

    public void setItemStorageArchives(Set<ItemStorageArchive> itemStorageArchives) {
        this.itemStorageArchives = itemStorageArchives;
    }
}
