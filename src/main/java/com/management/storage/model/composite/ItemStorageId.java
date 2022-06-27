package com.management.storage.model.composite;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@Builder
public class ItemStorageId implements Serializable {

    @Column(name = "item_id")
    private Long itemId;
    @Column(name = "storage_id")
    private Long storageId;

    public ItemStorageId() {
    }

    public ItemStorageId(Long itemId, Long storageId) {
        this.itemId = itemId;
        this.storageId = storageId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemStorageId itemStorageId = (ItemStorageId) o;
        return itemId.equals(itemStorageId.itemId) &&
                storageId.equals(itemStorageId.storageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, storageId);
    }
}
