package com.management.storage.services;

import com.management.storage.model.ItemProcurement;
import com.management.storage.model.ItemReceipt;
import com.management.storage.model.composite.ItemProcurementId;
import com.management.storage.model.composite.ItemReceiptId;
import com.management.storage.repository.ItemProcurementRepository;
import com.management.storage.repository.ItemReceiptRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ItemReceiptService {

    ItemReceiptRepository itemReceiptRepository;

    public Optional<ItemReceipt> findById(ItemReceiptId id) {
        return itemReceiptRepository.findById(id);
    }

    @Transactional
    public ItemReceipt createOrUpdate(ItemReceipt itemReceipt) {
        return itemReceiptRepository.save(itemReceipt);
    }
}