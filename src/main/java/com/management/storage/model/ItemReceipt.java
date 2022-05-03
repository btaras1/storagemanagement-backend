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

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name= "item_receipt")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ItemReceipt {
    @EmbeddedId
    @JsonIgnore
    private ItemReceiptId id;

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
    @JsonIgnore
    private Storage storage;

    @Column(name = "quantity")
    private Integer quantity;
}
