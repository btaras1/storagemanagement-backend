package com.management.storage.services;

import com.management.storage.model.Item;
import com.management.storage.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ItemService {

    ItemRepository itemRepository;


    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public List<Item> findAllByType(final String type) {
        return itemRepository.findAllByType(type.toUpperCase(Locale.ROOT));
    }

    public Item findById(final Long id) {
        return itemRepository.getById(id);
    }

    @Transactional
    public Item createOrUpdate(final Item item) {
        return itemRepository.save(item);
    }

    public void deleteById(final Long id) {
        itemRepository.deleteById(id);
    }

}
