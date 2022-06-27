package com.management.storage.ItemType;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.storage.model.Buyer;
import com.management.storage.model.Item;
import com.management.storage.model.ItemType;
import com.management.storage.repository.ItemTypeRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ItemTypeControllerIntegrationTest {

    static final int typeId = 999999;
    static final LocalDate mockedTime =
            LocalDate.now(Clock.fixed(Instant.parse("2022-06-20T10:00:00Z"), ZoneOffset.UTC));

    @Autowired
    ItemTypeRepository itemTypeRepository;

    @Autowired
    ItemTypeService itemTypeService;

    ItemType type = new ItemType();

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    ObjectMapper mapper;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        type.setValue("DOOR");
        type = itemTypeService.createOrUpdate(type);

        this.mvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @AfterEach
    public void cleanup() {
        itemTypeRepository.deleteAll();
    }

    @Test
    void findAllType() throws Exception {

        MvcResult mvcResult =
                this.mvc
                        .perform(get("/item-type")).andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();

        List<ItemType> result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        List<ItemType> expected = itemTypeRepository.findAll();

        assertTrue(result.containsAll(expected));
    }


    @Test
    void findTypeById() throws Exception {
        MvcResult mvcResult =
                this.mvc
                        .perform(get("/item-type/" + type.getId().toString())).andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();

        ItemType result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                ItemType.class);


        assertEquals(type, result);
    }

    @Test
    void create() throws Exception {
        ItemType newItemType = new ItemType();
        newItemType.setValue("Guide");

        MvcResult mvcResult =
                this.mvc
                        .perform(post("/item-type")
                                .contentType("application/json")
                                .content(mapper.writeValueAsString(newItemType)))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();

        ItemType result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                ItemType.class);

        ItemType expected = itemTypeRepository.findById(result.getId()).orElse(null);

        assertEquals(result, expected);
    }

    @Test
    void deleteType() throws Exception {

        MvcResult mvcResult =
                this.mvc
                        .perform(delete("/item-type/" + type.getId()))
                        .andExpect(status().isOk())
                        .andReturn();

        ItemType result = itemTypeRepository.findById(type.getId()).orElse(null);
        assertNull(result);
    }

    @Test
    void createTypeValidation() throws Exception {
        ItemType newItemType = new ItemType();

        this.mvc
                .perform(post("/item-type")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(newItemType)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void updateColorValidation() throws Exception {
        ItemType newItemType = new ItemType();

        this.mvc
                .perform(put("/item-type/" + type.getId())
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(newItemType)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    /*
    @Test
    void updateTypeByNonExistingId() throws Exception {
        this.mvc
                .perform(put("/buyer/" + typeId)
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(type)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }
    */


    @Test
    void findTypeByNonExistingId() throws Exception {
        this.mvc
                .perform(get("/item-type/" + typeId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void deleteTypeByNonExistingId() throws Exception {
        this.mvc
                .perform(delete("/item-type/" + typeId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void updateType() throws Exception {
        type.setValue("Update");

        MvcResult mvcResult =
                this.mvc
                        .perform(put("/item-type/" + type.getId().toString())
                                .contentType("application/json")
                                .content(mapper.writeValueAsString(type)))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();

        ItemType result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                ItemType.class);

        ItemType expected = itemTypeRepository.findById(result.getId()).orElse(null);

        assertEquals("Update", expected.getValue());
        assertEquals(expected, result);
    }

}
