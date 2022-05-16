package com.management.storage.model.composite;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
@Getter
@Setter
@Embeddable
public class ItemReceiptId implements Serializable {
    @Column(name = "item_id")
    private Long itemId;
    @Column(name = "receipt_id")
    private Long receiptId;
    @Column(name = "storage_id")
    private Long storageId;

    public ItemReceiptId() {
    }

    public ItemReceiptId(Long itemId, Long receiptId, Long storageId) {
        this.itemId = itemId;
        this.receiptId = receiptId;
        this.storageId = storageId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemReceiptId itemReceiptId = (ItemReceiptId) o;
        return itemId.equals(itemReceiptId.itemId) &&
                receiptId.equals(itemReceiptId.receiptId) &&
                storageId.equals(itemReceiptId.storageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, receiptId, storageId);
    }
}
