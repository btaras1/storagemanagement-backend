package com.management.storage.Storage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.storage.dto.request.SetMountRequestDto;
import com.management.storage.dto.response.*;
import com.management.storage.model.*;
import com.management.storage.model.composite.ItemReceiptId;
import com.management.storage.repository.*;
import com.management.storage.services.*;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class StorageControllerIntegrationTest {

    static final int storageId = 999999;
    static final LocalDate mockedTime =
            LocalDate.now(Clock.fixed(Instant.parse("2022-06-20T10:00:00Z"), ZoneOffset.UTC));

    @Autowired
    StorageRepository storageRepository;
    @Autowired
    StorageService storageService;

    Storage storage = new Storage();

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    ObjectMapper mapper;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {

        storage.setName("Storage");
        storage.setLocation("Backyard");
        storage.setCreated(mockedTime);
        storage.setModified(mockedTime);
        storage = storageRepository.save(storage);

        this.mvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @AfterEach
    public void cleanup() {
        storageRepository.deleteAll();
    }

    @Test
    void findAllStorages() throws Exception {

        MvcResult mvcResult =
                this.mvc
                        .perform(get("/storage")).andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();

        List<Storage> result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        List<Storage> expected = storageRepository.findAll();

        assertTrue(result.containsAll(expected));
    }

    @Test
    void findStoragetById() throws Exception {
        MvcResult mvcResult =
                this.mvc
                        .perform(get("/storage/" + storage.getId().toString())).andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();

        Storage result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                Storage.class);


        assertEquals(storage, result);
    }

    @Test
    void create() throws Exception {
        Storage newStorage = new Storage();
        newStorage.setName("Storage");
        newStorage.setLocation("Location");
        newStorage.setCreated(mockedTime);
        newStorage.setModified(mockedTime);

        MvcResult mvcResult =
                this.mvc
                        .perform(post("/storage")
                                .contentType("application/json")
                                .content(mapper.writeValueAsString(newStorage)))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();

        Storage result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                Storage.class);

        Storage expected = storageRepository.findById(result.getId()).orElse(null);

        assertEquals(result, expected);
    }

    @Test
    void deleteStorage() throws Exception {
        MvcResult mvcResult =
                this.mvc
                        .perform(delete("/storage/" + storage.getId()))
                        .andExpect(status().isOk())
                        .andReturn();

        Storage result = storageRepository.findById(storage.getId()).orElse(null);
        assertEquals(null, result);
    }

    @Test
    void createReceiptValidation() throws Exception {
        Storage newStorage = new Storage();

        this.mvc
                .perform(post("/storage")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(newStorage)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void updateStorageValidation() throws Exception {
        Storage newStorage = new Storage();

        this.mvc
                .perform(put("/storage/" + storage.getId())
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(newStorage)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }
    /*
    @Test
    void updateStorageByNonExistingId() throws Exception {

        this.mvc
                .perform(put("/storage/" )
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(storage)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }
     */

    @Test
    void findStorageByNonExistingId() throws Exception {
        this.mvc
                .perform(get("/storage/" + storageId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void deleteStorageByNonExistingId() throws Exception {
        this.mvc
                .perform(delete("/storage/" + storageId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void findAllItems() throws Exception {

        MvcResult mvcResult =
                this.mvc
                        .perform(get("/storage/items-storage")).andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();

        List<StorageItemsDetailResponseDto> result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        List<StorageItemsDetailResponseDto> expected = storageService.findAllItems();

        assertTrue(result.containsAll(expected));
    }

    @Test
    void countItems() throws Exception {

        MvcResult mvcResult =
                this.mvc
                        .perform(get("/storage/count-items")).andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();

        List<ItemsCountResponseDto> result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        List<ItemsCountResponseDto> expected = storageService.countItems();

        assertTrue(result.containsAll(expected));
    }

    @Test
    void updateStorage() throws Exception {
        storage.setLocation("Test Location");

        MvcResult mvcResult =
                this.mvc
                        .perform(put("/storage/" + storage.getId().toString())
                                .contentType("application/json")
                                .content(mapper.writeValueAsString(storage)))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();

        Storage result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                Storage.class);

        Storage expected = storageRepository.findById(result.getId()).orElse(null);

        assertEquals("Test Location", expected.getLocation());
        assertEquals(expected, result);
    }
}
