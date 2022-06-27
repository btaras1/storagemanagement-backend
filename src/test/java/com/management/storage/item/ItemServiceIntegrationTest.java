package com.management.storage.item;

import com.management.storage.model.Item;
import com.management.storage.model.ItemType;
import com.management.storage.repository.ItemRepository;
import com.management.storage.services.ItemService;
import com.management.storage.services.ItemTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
public class ItemServiceIntegrationTest {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemService itemService;

    @Autowired
    ItemTypeService itemTypeService;

    ItemType door = new ItemType();
    Item itemDoor = new Item();


    @BeforeEach
    public void before() {
        door.setValue("DOOR");
        door = itemTypeService.createOrUpdate(door);
        itemDoor.setValue("2250 X 3300");
        itemDoor.setItemType(door);
        itemDoor = itemService.createOrUpdate(itemDoor);
    }


    @Test
    void addItem() {

        Item newItem = new Item();
        newItem.setValue("Door1");
        newItem.setItemType(door);

        Item savedItem = itemService.createOrUpdate(newItem);
        Item foundItem = itemRepository.findById(savedItem.getId()).orElse(null);

        assertEquals(savedItem.getId(), foundItem.getId());
    }

    @Test
    void editItem() {
        Item firstItem = itemRepository.findAll().stream().findFirst().orElse(null);
        firstItem.setValue("Edited item");
        Item editedItem = itemService.createOrUpdate(firstItem);

        assertEquals(firstItem.getId(), editedItem.getId());

    }

    @Test
    void deleteItem() {
        Item firstItem = itemRepository.findAll().stream().findFirst().orElse(null);
        itemService.deleteById(firstItem.getId());
        Item deletedItem = itemRepository.findById(firstItem.getId()).orElse(null);

        assertNull(deletedItem);
    }

    @Test
    void findAllItemsByType() {
        assertTrue(itemService.findAllByType("DOOR").size() == 1);
    }

    @Test
    void findAllItems() {
        assertTrue(itemService.findAll().size() == 1);
    }

    @Test
    void findById() {
        assertEquals(itemDoor.getId(), itemService.findById(itemDoor.getId()).getId());
    }


}
