package com.management.storage.services;

import com.management.storage.exception.ApiError;
import com.management.storage.exception.StorageManagementException;
import com.management.storage.model.ItemType;
import com.management.storage.repository.ItemTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ItemTypeService {

    ItemTypeRepository itemTypeRepository;

    public List<ItemType> findAll() {
        return itemTypeRepository.findAll();
    }

    public ItemType findById(Long id) throws StorageManagementException {
        return itemTypeRepository.findById(id).orElseThrow(() -> new StorageManagementException(ApiError.ITEM_TYPE_NOT_FOUND));
    }

    @Transactional
    public ItemType createOrUpdate(ItemType type) {
        return itemTypeRepository.save(type);
    }

    public void deleteById(Long id) {
        itemTypeRepository.deleteById(id);
    }
}
