package com.management.storage.repository;

import com.management.storage.model.ItemStorage;
import com.management.storage.model.composite.ItemStorageId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemStorageRepository extends JpaRepository<ItemStorage, ItemStorageId> {
}
