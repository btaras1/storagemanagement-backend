package com.management.storage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.management.storage.model.composite.ItemStorageArchiveId;
import com.management.storage.model.composite.ItemStorageId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name= "item_storage_archive")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ItemStorageArchive {

    @EmbeddedId
    @JsonIgnore
    private ItemStorageArchiveId id;

    @ManyToOne
    @MapsId("itemId")
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @MapsId("storageArchiveId")
    @JoinColumn(name = "storage_archive_id")
    @JsonIgnore
    private StorageArchive storageArchive;

    @Column(name = "quantity")
    private Integer quantity;

    public ItemStorageArchive() {
    }

    public ItemStorageArchive(Item item, StorageArchive storageArchive, Integer quantity) {
        this.item = item;
        this.storageArchive = storageArchive;
        this.quantity = quantity;
    }

    public ItemStorageArchiveId getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public StorageArchive getStorageArchive() {
        return storageArchive;
    }

    public void setStorageArchive(StorageArchive storageArchive) {
        this.storageArchive = storageArchive;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
