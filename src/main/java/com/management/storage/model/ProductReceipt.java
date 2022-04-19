package com.management.storage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.management.storage.model.composite.ItemStorageId;
import com.management.storage.model.composite.ProductReceiptId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@Entity
@Table(name= "product_receipt")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductReceipt {
    @EmbeddedId
    @JsonIgnore
    private ProductReceiptId id;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @MapsId("receiptId")
    @JoinColumn(name = "receipt_id")
    @JsonIgnore
    private Receipt receipt;

    @Column(name = "quantity")
    private Integer quantity;

    public ProductReceipt() {
    }

    public ProductReceipt(Product product, Receipt receipt, Integer quantity) {
        this.product = product;
        this.receipt = receipt;
        this.quantity = quantity;
    }
}
