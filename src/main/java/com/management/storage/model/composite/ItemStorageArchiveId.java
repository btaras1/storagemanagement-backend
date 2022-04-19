package com.management.storage.model.composite;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ItemStorageArchiveId implements Serializable {
    @Column(name = "item_id")
    private Long itemId;
    @Column(name = "storage_archive_id")
    private Long storageArchiveId;


    public ItemStorageArchiveId() {
    }

    public ItemStorageArchiveId(Long itemId, Long storageArchiveId) {
        this.itemId = itemId;
        this.storageArchiveId = storageArchiveId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemStorageArchiveId itemStorageArchiveId = (ItemStorageArchiveId) o;
        return itemId.equals(itemStorageArchiveId.itemId) &&
                storageArchiveId.equals(itemStorageArchiveId.storageArchiveId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, storageArchiveId);
    }
}
