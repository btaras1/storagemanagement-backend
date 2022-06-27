package com.management.storage.services;

import com.management.storage.model.Color;
import com.management.storage.model.ItemProcurement;
import com.management.storage.model.composite.ItemProcurementId;
import com.management.storage.repository.ItemProcurementRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ItemProcurementService {

    ItemProcurementRepository itemProcurementRepository;

    public ItemProcurement findById(ItemProcurementId id) {
        return itemProcurementRepository.getById(id);
    }

    @Transactional
    public ItemProcurement createOrUpdate(ItemProcurement itemProcurement) {
        return itemProcurementRepository.save(itemProcurement);
    }
}
