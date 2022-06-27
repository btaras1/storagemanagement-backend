package com.management.storage.Buyer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.storage.dto.response.MostBuyersFromCityDto;
import com.management.storage.dto.response.MostBuyersFromCityResponse;
import com.management.storage.model.Buyer;
import com.management.storage.model.Receipt;
import com.management.storage.repository.BuyerRepository;
import com.management.storage.repository.ReceiptRepository;
import com.management.storage.services.BuyerService;
import com.management.storage.services.ReceiptService;
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
public class BuyerControllerIntegrationTest {

    static final int buyerId = 999999;
    static final LocalDate mockedTime =
            LocalDate.now(Clock.fixed(Instant.parse("2022-06-20T10:00:00Z"), ZoneOffset.UTC));

    @Autowired
    BuyerRepository buyerRepository;

    @Autowired
    ReceiptRepository receiptRepository;

    @Autowired
    BuyerService buyerService;

    @Autowired
    ReceiptService receiptService;

    Buyer buyer = new Buyer();
    Receipt receipt = new Receipt();

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    ObjectMapper mapper;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        buyer.setFirstname("John");
        buyer.setLastname("Doe");
        buyer.setAddress("Central Park");
        buyer.setCity("NY");
        buyer.setMobile("0987654321");
        buyer.setCreated(mockedTime);
        buyer.setModified(mockedTime);
        buyer = buyerService.createOrUpdate(buyer);

        receipt.setSold(mockedTime);
        receipt.setModified(mockedTime);
        receipt.setMountedDate(mockedTime);
        receipt.setDocumentId("1000-PPV");
        receipt.setDescription("Some description");
        receipt.setCreated(mockedTime);
        receipt.setBuyer(buyer);
        receipt = receiptService.create(receipt);

        this.mvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @AfterEach
    public void cleanup() {
        receiptRepository.deleteAll();
        buyerRepository.deleteAll();
    }

    @Test
    void findAllBuyers() throws Exception {

        MvcResult mvcResult =
                this.mvc
                        .perform(get("/buyer")).andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();

        List<Buyer> result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        List<Buyer> expected = buyerRepository.findAll();

        assertTrue(result.containsAll(expected));
    }

    @Test
    void findMostBuyersFromCity() throws Exception {

        MvcResult mvcResult =
                this.mvc
                        .perform(get("/buyer/top-city")).andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();

        MostBuyersFromCityResponse result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        MostBuyersFromCityDto realResult = buyerService.mostBuyersFromCity();
        MostBuyersFromCityResponse expected = new MostBuyersFromCityResponse();
        expected.setCount(realResult.getCount());
        expected.setCity(realResult.getCity());

        assertEquals(expected, result);
    }

    @Test
    void findBuyerById() throws Exception {
        MvcResult mvcResult =
                this.mvc
                        .perform(get("/buyer/" + buyer.getId().toString())).andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();

        Buyer result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                Buyer.class);


        assertEquals(buyer, result);
    }

    @Test
    void create() throws Exception {
        Buyer newBuyer = new Buyer();
        newBuyer.setFirstname("Dan");
        newBuyer.setLastname("Moe");
        newBuyer.setAddress("Central House");
        newBuyer.setCity("LA");
        newBuyer.setMobile("0912345678");
        newBuyer.setCreated(mockedTime);
        newBuyer.setModified(mockedTime);

        MvcResult mvcResult =
                this.mvc
                        .perform(post("/buyer")
                                .contentType("application/json")
                                .content(mapper.writeValueAsString(newBuyer)))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();

        Buyer result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                Buyer.class);

        Buyer expected = buyerRepository.findById(result.getId()).orElse(null);

        assertEquals(result, expected);
    }

    @Test
    void deleteBuyer() throws Exception {
        receiptRepository.deleteAll();

        MvcResult mvcResult =
                this.mvc
                        .perform(delete("/buyer/" + buyer.getId()))
                        .andExpect(status().isOk())
                        .andReturn();

        Buyer result = buyerRepository.findById(buyer.getId()).orElse(null);
        assertNull(result);
    }

    @Test
    void createBuyerValidation() throws Exception {
        Buyer newBuyer = new Buyer();

        this.mvc
                .perform(post("/buyer")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(newBuyer)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void updateBuyerValidation() throws Exception {
        Buyer newBuyer = new Buyer();

        this.mvc
                .perform(put("/buyer")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(newBuyer)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    /*
    @Test
    void updateBuyerByNonExistingId() throws Exception {
        this.mvc
                .perform(put("/buyer/" + buyerId)
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(buyer)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }
    */

    @Test
    void findBuyerByNonExistingId() throws Exception {
        this.mvc
                .perform(get("/buyer/" + buyerId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void deleteBuyerByNonExistingId() throws Exception {
        this.mvc
                .perform(delete("/buyer/" + buyerId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void updateBuyer() throws Exception {
        buyer.setFirstname("Update");

        MvcResult mvcResult =
                this.mvc
                        .perform(put("/buyer")
                                .contentType("application/json")
                                .content(mapper.writeValueAsString(buyer)))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();

        Buyer result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                Buyer.class);

        Buyer expected = buyerRepository.findById(result.getId()).orElse(null);

        assertEquals("Update", expected.getFirstname());
        assertEquals(expected, result);
    }

}
