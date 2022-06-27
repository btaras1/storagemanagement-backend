package com.management.storage.repository;

import com.management.storage.model.ItemReceipt;
import com.management.storage.model.composite.ItemReceiptId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemReceiptRepository extends JpaRepository<ItemReceipt, ItemReceiptId> {
}
