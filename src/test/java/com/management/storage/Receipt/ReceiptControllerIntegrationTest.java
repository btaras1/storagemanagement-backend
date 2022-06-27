package com.management.storage.Receipt;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.storage.dto.request.SetMountRequestDto;
import com.management.storage.dto.response.MostSelledDoor;
import com.management.storage.dto.response.MostSelledDoorResponseDto;
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
public class ReceiptControllerIntegrationTest {

    static final int receiptId = 999999;
    static final LocalDate mockedTime =
            LocalDate.now(Clock.fixed(Instant.parse("2022-06-20T10:00:00Z"), ZoneOffset.UTC));

    @Autowired
    ReceiptService receiptService;
    @Autowired
    ReceiptRepository receiptRepository;
    @Autowired
    BuyerRepository buyerRepository;
    @Autowired
    ColorRepository colorRepository;
    @Autowired
    BuyerService buyerService;
    @Autowired
    ColorService colorService;
    @Autowired
    ItemService itemService;
    @Autowired
    ItemTypeService typeService;
    @Autowired
    ItemTypeRepository typeRepository;
    @Autowired
    StorageRepository storageRepository;
    @Autowired
    ItemReceiptRepository itemReceiptRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    EmployeeService employeeService;

    Receipt receipt = new Receipt();
    Buyer buyer = new Buyer();
    Color color = new Color();
    Item item = new Item();
    ItemType type = new ItemType();
    Storage storage = new Storage();
    Employee employee = new Employee();


    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    ObjectMapper mapper;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        employee.setFirstname("John");
        employee.setFirstname("Doe");
        employee.setCreated(mockedTime);
        employee.setModified(mockedTime);
        employee = employeeService.createOrUpdate(employee);
        storage.setName("Storage");
        storage.setLocation("Backyard");
        storage.setCreated(mockedTime);
        storage.setModified(mockedTime);
        storage = storageRepository.save(storage);

        color.setValue("Black");
        color = colorService.createOrUpdate(color);

        type.setValue("DOOR");
        type = typeService.createOrUpdate(type);

        item.setCreated(mockedTime);
        item.setModified(mockedTime);
        item.setValue("2250 X 3300");
        item.setItemType(type);
        item.setColor(color);
        item = itemService.createOrUpdate(item);

        buyer.setFirstname("John");
        buyer.setLastname("Doe");
        buyer.setAddress("Central Park");
        buyer.setCity("NY");
        buyer.setMobile("0987654321");
        buyer.setCreated(mockedTime);
        buyer.setModified(mockedTime);
        buyer = buyerService.createOrUpdate(buyer);

        receipt.setDocumentId("PPV-123659");
        receipt.setSold(mockedTime);
        receipt.setMountedDate(mockedTime);
        receipt.setCreated(mockedTime);
        receipt.setModified(mockedTime);
        receipt.setDescription("Description");
        receipt.setBuyer(buyer);
        receipt = receiptService.create(receipt);

        ItemReceiptId itemReceiptId = new ItemReceiptId();
        itemReceiptId.setItemId(item.getId());
        itemReceiptId.setStorageId(storage.getId());
        itemReceiptId.setReceiptId(receipt.getId());

        ItemReceipt itemReceipt = new ItemReceipt();
        itemReceipt.setId(itemReceiptId);
        itemReceipt.setItem(item);
        itemReceipt.setReceipt(receipt);
        itemReceipt.setStorage(storage);
        itemReceipt.setQuantity(2);
        itemReceipt = itemReceiptRepository.saveAndFlush(itemReceipt);

        Set<ItemReceipt> itemReceipts =new HashSet<>();
        itemReceipts.add(itemReceipt);

        receipt.setItemReceipts(itemReceipts);
        receipt = receiptRepository.saveAndFlush(receipt);

        this.mvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @AfterEach
    public void cleanup() {
        itemReceiptRepository.deleteAll();
        receiptRepository.deleteAll();
        buyerRepository.deleteAll();
        itemRepository.deleteAll();
        storageRepository.deleteAll();
        colorRepository.deleteAll();
        typeRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @Test
    void findAllReceipt() throws Exception {

        MvcResult mvcResult =
                this.mvc
                        .perform(get("/receipt")).andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();

        List<Receipt> result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        List<Receipt> expected = receiptRepository.findAll();

        assertTrue(result.containsAll(expected));
    }

    @Test
    void findReceiptById() throws Exception {
        MvcResult mvcResult =
                this.mvc
                        .perform(get("/receipt/" + receipt.getId().toString())).andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();

        Receipt result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                Receipt.class);


        assertEquals(receipt, result);
    }

    @Test
    void create() throws Exception {
        Receipt newReceipt = new Receipt();
        newReceipt.setDocumentId("PPV-123658");
        newReceipt.setSold(mockedTime);
        newReceipt.setMountedDate(mockedTime);
        newReceipt.setCreated(mockedTime);
        newReceipt.setModified(mockedTime);
        newReceipt.setDescription("Description");
        newReceipt.setBuyer(buyer);
        Set<ItemReceipt> itemReceipts = new HashSet<>();
        itemReceipts.add(new ItemReceipt(item, storage, 1));
        newReceipt.setItemReceipts(itemReceipts);

        MvcResult mvcResult =
                this.mvc
                        .perform(post("/receipt")
                                .contentType("application/json")
                                .content(mapper.writeValueAsString(newReceipt)))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();

        Receipt result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                Receipt.class);

        Receipt expected = receiptRepository.findById(result.getId()).orElse(null);

        assertEquals(result, expected);
    }

    @Test
    void deleteReceipt() throws Exception {
        MvcResult mvcResult =
                this.mvc
                        .perform(delete("/receipt/" + receipt.getId()))
                        .andExpect(status().isOk())
                        .andReturn();

        Receipt result = receiptRepository.findById(receipt.getId()).orElse(null);
        assertEquals(null, result);
    }

    @Test
    void createReceiptValidation() throws Exception {
        Receipt newReceipt = new Receipt();

        this.mvc
                .perform(post("/receipt")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(newReceipt)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void updateReceiptValidation() throws Exception {
        Receipt newReceipt = new Receipt();

        this.mvc
                .perform(put("/receipt")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(newReceipt)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    /*
    @Test
    void updateReceiptByNonExistingId() throws Exception {
        Receipt updateReceipt = new Receipt();
        updateReceipt.setDocumentId("PPV-123657");
        updateReceipt.setSold(mockedTime);
        updateReceipt.setMountedDate(mockedTime);
        updateReceipt.setCreated(mockedTime);
        updateReceipt.setModified(mockedTime);
        updateReceipt.setDescription("Description");
        updateReceipt.setBuyer(buyer);

        this.mvc
                .perform(put("/receipt/" + receiptId)
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(updateReceipt)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }
    */

    /*
    @Test
    void findProcurementByNonExistingId() throws Exception {
        this.mvc
                .perform(get("/receipt/" + receiptId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }
    */

    @Test
    void deleteProcurementByNonExistingId() throws Exception {
        this.mvc
                .perform(delete("/receipt/" + receiptId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void getDocumentPdf() throws Exception {
        this.mvc
                .perform(get("/receipt/download/" + receipt.getId())).andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void getMostSelledDoor() throws Exception {

        MvcResult mvcResult =
                this.mvc
                        .perform(get("/receipt/most-selled-door")).andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();

        MostSelledDoorResponseDto result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        MostSelledDoor expected = receiptRepository.mostSoldDoor();
        Color doorColor = colorRepository.findById(expected.getColor_Id()).orElse(null);
        MostSelledDoorResponseDto expectedMap = new MostSelledDoorResponseDto(expected.getValue() + " - " + doorColor.getValue(), expected.getCount());
        assertEquals(expectedMap, result);
    }

    @Test
    void countReceiptsForCurrentMonth() throws Exception {

        MvcResult mvcResult =
                this.mvc
                        .perform(get("/receipt/count-receipts-current-month")).andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();

        Integer result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        Integer expected = receiptRepository.countReceiptsForCurrentMonth();
        assertEquals(expected, result);
    }

    @Test
    void setMounted() throws Exception {
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        SetMountRequestDto mountRequest = new SetMountRequestDto(employees, "Description");

        MvcResult mvcResult =
                this.mvc
                        .perform(put("/receipt/mount/" + receipt.getId().toString())
                                .contentType("application/json")
                                .content(mapper.writeValueAsString(mountRequest)))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();

        Receipt result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                Receipt.class);

        Receipt expected = receiptRepository.findById(result.getId()).orElse(null);

        assertEquals(result, expected);
    }

    @Test
    void getAllNonMounted() throws Exception {

        MvcResult mvcResult =
                this.mvc
                        .perform(get("/receipt/mount-false"))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();

        List<Receipt> result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        List<Receipt> expected = receiptRepository.getReceiptNotMounted();

        assertEquals(result, expected);
    }

    @Test
    void updateReceipt() throws Exception {
        receipt.setDocumentId("DocumentID");
        MvcResult mvcResult =
                this.mvc
                        .perform(put("/receipt")
                                .contentType("application/json")
                                .content(mapper.writeValueAsString(receipt)))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();

        Receipt result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                Receipt.class);

        Receipt expected = receiptRepository.findById(result.getId()).orElse(null);

        assertEquals("DocumentID", expected.getDocumentId());
        assertEquals(expected, result);
    }

}
