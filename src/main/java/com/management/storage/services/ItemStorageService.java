package com.management.storage.services;

import com.management.storage.model.ItemProcurement;
import com.management.storage.model.ItemStorage;
import com.management.storage.model.composite.ItemProcurementId;
import com.management.storage.model.composite.ItemStorageId;
import com.management.storage.repository.ItemProcurementRepository;
import com.management.storage.repository.ItemStorageRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ItemStorageService {

    ItemStorageRepository itemStorageRepository;

    public ItemStorage findById(ItemStorageId id) {
        return itemStorageRepository.findById(id).orElse(null);
    }

    @Transactional
    public ItemStorage createOrUpdate(ItemStorage itemStorage) {
        return itemStorageRepository.save(itemStorage);
    }
}
