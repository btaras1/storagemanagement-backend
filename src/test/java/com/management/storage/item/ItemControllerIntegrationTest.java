package com.management.storage.item;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.storage.model.Buyer;
import com.management.storage.model.Item;
import com.management.storage.model.ItemType;
import com.management.storage.repository.ItemRepository;
import com.management.storage.repository.ItemTypeRepository;
import com.management.storage.services.ItemService;
import com.management.storage.services.ItemTypeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ItemControllerIntegrationTest {

    static final int itemId = 999999;
    static final LocalDate mockedTime =
            LocalDate.now(Clock.fixed(Instant.parse("2022-06-20T10:00:00Z"), ZoneOffset.UTC));

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemTypeRepository typeRepository;
    @Autowired
    ItemService itemService;
    @Autowired
    ItemTypeService itemTypeService;
    ItemType door = new ItemType();
    ItemType guide = new ItemType();
    Item itemDoor = new Item();
    Item itemGuide = new Item();
    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    ObjectMapper mapper;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        door.setValue("DOOR");
        door = itemTypeService.createOrUpdate(door);
        guide.setValue("GUIDE");
        guide = itemTypeService.createOrUpdate(guide);

        itemDoor.setCreated(mockedTime);
        itemDoor.setModified(mockedTime);
        itemDoor.setValue("2250 X 3300");
        itemDoor.setItemType(door);
        itemDoor = itemService.createOrUpdate(itemDoor);

        itemGuide.setValue("VODILICA M");
        itemGuide.setItemType(guide);
        itemGuide.setCreated(mockedTime);
        itemGuide.setModified(mockedTime);
        itemGuide = itemService.createOrUpdate(itemGuide);


        this.mvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @AfterEach
    public void cleanup() {
        itemRepository.deleteAll();
        typeRepository.deleteAll();
    }

    @Test
    void findAllItems() throws Exception {

        MvcResult mvcResult =
                this.mvc
                        .perform(get("/item")).andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();

        List<Item> result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        List<Item> expected = itemRepository.findAll();

        assertTrue(result.containsAll(expected));
    }

    @Test
    void findAllItemsByType() throws Exception {

        MvcResult mvcResult =
                this.mvc
                        .perform(get("/item/type/door")).andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();


        List<Item> result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<Item>>() {
                });

        List<Item> expected = itemRepository.findAllByType("DOOR");
        boolean isSpecificType = result.stream().allMatch(item -> item.getItemType().getValue().equals("DOOR"));


        assertTrue(isSpecificType && result.containsAll(expected));
    }

    @Test
    void findItemById() throws Exception {
        MvcResult mvcResult =
                this.mvc
                        .perform(get("/item/" + itemDoor.getId().toString())).andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();

        Item result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                Item.class);


        assertEquals(itemDoor, result);
    }

    @Test
    void create() throws Exception {
        Item newDoor = new Item();
        newDoor.setValue("2222 X 3333");
        newDoor.setItemType(door);

        MvcResult mvcResult =
                this.mvc
                        .perform(post("/item")
                                .contentType("application/json")
                                .content(mapper.writeValueAsString(newDoor)))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();

        Item result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                Item.class);

        Item expected = itemRepository.findById(result.getId()).orElse(null);

        assertEquals(result, expected);
    }

    @Test
    void deleteItem() throws Exception {
        MvcResult mvcResult =
                this.mvc
                        .perform(delete("/item/" + itemDoor.getId()))
                        .andExpect(status().isOk())
                        .andReturn();

        Item result = itemRepository.findById(itemDoor.getId()).orElse(null);
        assertEquals(null, result);
    }

    @Test
    void createItemValidation() throws Exception {
        Item newDoor = new Item();

        this.mvc
                .perform(post("/item")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(newDoor)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void updateItemValidation() throws Exception {
        Item newDoor = new Item();

        this.mvc
                .perform(put("/item")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(newDoor)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    /*
    @Test
    void updateItemByNonExistingId() throws Exception {
        this.mvc
                .perform(put("/item/" + itemId)
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(itemDoor)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }
    */

    @Test
    void findAllByWrongType() throws Exception {
        this.mvc
                .perform(get("/item/type/car"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void findItemByNonExistingId() throws Exception {
        this.mvc
                .perform(get("/item/" + itemId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void deleteItemByNonExistingId() throws Exception {
        this.mvc
                .perform(delete("/item/" + itemId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void updateItem() throws Exception {
        itemDoor.setValue("Update");

        MvcResult mvcResult =
                this.mvc
                        .perform(put("/item")
                                .contentType("application/json")
                                .content(mapper.writeValueAsString(itemDoor)))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();

        Item result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                Item.class);

        Item expected = itemRepository.findById(result.getId()).orElse(null);

        assertEquals("Update", expected.getValue());
        assertEquals(expected, result);
    }

}
