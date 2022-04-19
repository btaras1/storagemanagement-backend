package com.management.storage.model.composite;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductReceiptId implements Serializable {
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "receipt_id")
    private Long receiptId;


    public ProductReceiptId() {
    }

    public ProductReceiptId(Long productId, Long receiptId) {
        this.productId = productId;
        this.receiptId = receiptId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductReceiptId productReceiptId = (ProductReceiptId) o;
        return productId.equals(productReceiptId.productId) &&
                receiptId.equals(productReceiptId.receiptId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, receiptId);
    }
}
