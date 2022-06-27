package com.management.storage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.management.storage.model.composite.ItemProcurementId;
import com.management.storage.model.composite.ItemReceiptId;
import com.management.storage.model.composite.ItemStorageId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "item_receipt")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ItemReceipt {
    @EmbeddedId
    private ItemReceiptId id = new ItemReceiptId();

    @ManyToOne
    @MapsId("itemId")
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @MapsId("receiptId")
    @JoinColumn(name = "receipt_id")
    @JsonIgnore
    private Receipt receipt;

    @ManyToOne
    @MapsId("storageId")
    @JoinColumn(name = "storage_id")
    private Storage storage;

    @Column(name = "quantity")
    private Integer quantity;

    public ItemReceipt(Item item, Receipt receipt, Storage storage, Integer quantity) {
        this.item = item;
        this.receipt = receipt;
        this.storage = storage;
        this.quantity = quantity;
    }

    public ItemReceipt(Item item, Storage storage, Integer quantity) {
        this.item = item;
        this.storage = storage;
        this.quantity = quantity;
    }

    public ItemReceiptId getId() {
        return id;
    }

    public void setId(ItemReceiptId id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
