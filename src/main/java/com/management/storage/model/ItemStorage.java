package com.management.storage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.management.storage.model.composite.ItemStorageId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name= "item_storage")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ItemStorage {
    @EmbeddedId
    @JsonIgnore
    private ItemStorageId id;

    @ManyToOne
    @MapsId("itemId")
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @MapsId("storageId")
    @JoinColumn(name = "storage_id")
    @JsonIgnore
    private Storage storage;

    @Column(name = "quantity")
    private Integer quantity;

    public ItemStorage() {
    }

    public ItemStorage(Item item, Storage storage, Integer quantity) {
        this.item = item;
        this.storage = storage;
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
