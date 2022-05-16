package com.management.storage.repository;

import com.management.storage.model.ItemProcurement;
import com.management.storage.model.composite.ItemProcurementId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemProcurementRepository extends JpaRepository<ItemProcurement, ItemProcurementId> {
}
