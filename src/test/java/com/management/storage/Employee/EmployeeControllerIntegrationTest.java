package com.management.storage.Employee;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.storage.model.Buyer;
import com.management.storage.model.Employee;
import com.management.storage.repository.EmployeeRepository;
import com.management.storage.services.EmployeeService;
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
public class EmployeeControllerIntegrationTest {

    static final int employeeId = 999999;
    static final LocalDate mockedTime =
            LocalDate.now(Clock.fixed(Instant.parse("2022-06-20T10:00:00Z"), ZoneOffset.UTC));

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeService employeeService;

    Employee employee = new Employee();

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    ObjectMapper mapper;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        employee.setFirstname("John");
        employee.setLastname("Doe");
        employee.setCreated(mockedTime);
        employee.setModified(mockedTime);
        employee = employeeService.createOrUpdate(employee);

        this.mvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @AfterEach
    public void cleanup() {
        employeeRepository.deleteAll();
    }

    @Test
    void findAllBuyers() throws Exception {

        MvcResult mvcResult =
                this.mvc
                        .perform(get("/employee")).andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();

        List<Employee> result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        List<Employee> expected = employeeRepository.findAll();

        assertTrue(result.containsAll(expected));
    }

    @Test
    void findEmployeeById() throws Exception {
        MvcResult mvcResult =
                this.mvc
                        .perform(get("/employee/" + employee.getId().toString())).andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();

        Employee result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                Employee.class);


        assertEquals(employee, result);
    }

    @Test
    void create() throws Exception {
        Employee newEmployee = new Employee();
        newEmployee.setFirstname("Dan");
        newEmployee.setLastname("Moe");
        newEmployee.setCreated(mockedTime);
        newEmployee.setModified(mockedTime);

        MvcResult mvcResult =
                this.mvc
                        .perform(post("/employee")
                                .contentType("application/json")
                                .content(mapper.writeValueAsString(newEmployee)))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();

        Employee result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                Employee.class);

        Employee expected = employeeRepository.findById(result.getId()).orElse(null);

        assertEquals(result, expected);
    }

    @Test
    void deleteEmployee() throws Exception {

        MvcResult mvcResult =
                this.mvc
                        .perform(delete("/employee/" + employee.getId()))
                        .andExpect(status().isOk())
                        .andReturn();

        Employee result = employeeRepository.findById(employee.getId()).orElse(null);
        assertNull(result);
    }

    @Test
    void createEmployeeValidation() throws Exception {
        Employee newEmployee = new Employee();

        this.mvc
                .perform(post("/employee")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(newEmployee)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void updateEmployeeValidation() throws Exception {
        Employee newEmployee = new Employee();

        this.mvc
                .perform(put("/employee")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(newEmployee)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }
    /*
    @Test
    void updateEmployeeByNonExistingId() throws Exception {
        this.mvc
                .perform(put("/employee/" + employeeId)
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(employee)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }
    */

    @Test
    void findEmployeeByNonExistingId() throws Exception {
        this.mvc
                .perform(get("/employee/" + employeeId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void deleteBuyerByNonExistingId() throws Exception {
        this.mvc
                .perform(delete("/employee/" + employeeId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void count() throws Exception {

        MvcResult mvcResult =
                this.mvc
                        .perform(get("/employee/count"))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();

        Long result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                Long.class);


        assertEquals(result, 1);
    }

    @Test
    void updateEmployee() throws Exception {
        employee.setFirstname("Update");

        MvcResult mvcResult =
                this.mvc
                        .perform(put("/employee")
                                .contentType("application/json")
                                .content(mapper.writeValueAsString(employee)))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();

        Employee result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                Employee.class);

        Employee expected = employeeRepository.findById(result.getId()).orElse(null);

        assertEquals("Update", expected.getFirstname());
        assertEquals(expected, result);
    }

}
