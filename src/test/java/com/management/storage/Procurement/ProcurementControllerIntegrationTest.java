package com.management.storage.Procurement;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.storage.model.Buyer;
import com.management.storage.model.Procurement;
import com.management.storage.model.Storage;
import com.management.storage.repository.ProcurementRepository;
import com.management.storage.repository.StorageRepository;
import com.management.storage.services.ProcurementService;
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
public class ProcurementControllerIntegrationTest {

    static final int procurementId = 999999;
    static final LocalDate mockedTime = LocalDate.now(Clock.fixed(Instant.parse("2022-06-20T10:00:00Z"), ZoneOffset.UTC));

    @Autowired
    ProcurementService procurementService;
    @Autowired
    ProcurementRepository procurementRepository;
    @Autowired
    StorageRepository storageRepository;

    Procurement procurement = new Procurement();
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

        procurement.setDocumentId("PPV-111029");
        procurement.setCreated(mockedTime);
        procurement.setModified(mockedTime);
        procurement.setStorage(storage);
        procurement = procurementService.create(procurement);

        this.mvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @AfterEach
    public void cleanup() {
        procurementRepository.deleteAll();
    }

    @Test
    void findAllProcurement() throws Exception {

        MvcResult mvcResult = this.mvc.perform(get("/procurement")).andDo(print()).andExpect(status().isOk()).andReturn();

        List<Procurement> result = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });

        List<Procurement> expected = procurementRepository.findAll();

        assertTrue(result.containsAll(expected));
    }

    @Test
    void findProcurementById() throws Exception {
        MvcResult mvcResult = this.mvc.perform(get("/procurement/" + procurement.getId().toString())).andDo(print()).andExpect(status().isOk()).andReturn();

        Procurement result = mapper.readValue(mvcResult.getResponse().getContentAsString(), Procurement.class);


        assertEquals(procurement, result);
    }

    @Test
    void create() throws Exception {
        Procurement newProcurement = new Procurement();
        newProcurement.setDocumentId("PPV-111031");
        newProcurement.setCreated(mockedTime);
        newProcurement.setModified(mockedTime);

        MvcResult mvcResult = this.mvc.perform(post("/procurement").contentType("application/json").content(mapper.writeValueAsString(newProcurement))).andDo(print()).andExpect(status().isOk()).andReturn();

        Procurement result = mapper.readValue(mvcResult.getResponse().getContentAsString(), Procurement.class);

        Procurement expected = procurementRepository.findById(result.getId()).orElse(null);

        assertEquals(result, expected);
    }

    @Test
    void deleteProcurement() throws Exception {
        MvcResult mvcResult = this.mvc.perform(delete("/procurement/" + procurement.getId())).andExpect(status().isOk()).andReturn();

        Procurement result = procurementRepository.findById(procurement.getId()).orElse(null);
        assertEquals(null, result);
    }

    @Test
    void createProcurementValidation() throws Exception {
        Procurement newProcurement = new Procurement();

        this.mvc.perform(post("/procurement").contentType("application/json").content(mapper.writeValueAsString(newProcurement))).andDo(print()).andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void updateProcurementValidation() throws Exception {
        Procurement newProcurement = new Procurement();

        this.mvc.perform(put("/procurement").contentType("application/json").content(mapper.writeValueAsString(newProcurement))).andDo(print()).andExpect(status().isBadRequest()).andReturn();
    }

    /*
    @Test
    void updateProcurementByNonExistingId() throws Exception {
        Procurement updateProcurement = new Procurement();
        updateProcurement.setDocumentId("PPV-111029");
        updateProcurement.setCreated(mockedTime);
        updateProcurement.setModified(mockedTime);
        updateProcurement.setStorage(storage);

        this.mvc.perform(put("/procurement/" + procurementId).contentType("application/json").content(mapper.writeValueAsString(updateProcurement))).andDo(print()).andExpect(status().isNotFound()).andReturn();
    }
    */


    @Test
    void findProcurementByNonExistingId() throws Exception {
        this.mvc.perform(get("/procurement/" + procurementId)).andDo(print()).andExpect(status().isNotFound()).andReturn();
    }

    @Test
    void deleteProcurementByNonExistingId() throws Exception {
        this.mvc.perform(delete("/procurement/" + procurementId)).andDo(print()).andExpect(status().isNotFound()).andReturn();
    }

    @Test
    void getDocumentPdf() throws Exception {
        this.mvc.perform(get("/procurement/download/" + procurement.getId())).andDo(print()).andExpect(status().isOk()).andReturn();
    }

    @Test
    void updateProcurement() throws Exception {
        procurement.setDocumentId("DocumentID");

        MvcResult mvcResult =
                this.mvc
                        .perform(put("/procurement")
                                .contentType("application/json")
                                .content(mapper.writeValueAsString(procurement)))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();

        Procurement result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                Procurement.class);

        Procurement expected = procurementRepository.findById(result.getId()).orElse(null);

        assertEquals("DocumentID", expected.getDocumentId());
        assertEquals(expected, result);
    }
}
