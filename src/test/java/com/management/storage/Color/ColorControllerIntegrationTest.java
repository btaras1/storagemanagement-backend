package com.management.storage.Color;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.storage.model.Buyer;
import com.management.storage.model.Color;
import com.management.storage.repository.ColorRepository;
import com.management.storage.services.ColorService;
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
public class ColorControllerIntegrationTest {

    static final int colorId = 999999;
    static final LocalDate mockedTime =
            LocalDate.now(Clock.fixed(Instant.parse("2022-06-20T10:00:00Z"), ZoneOffset.UTC));

    @Autowired
    ColorRepository colorRepository;

    @Autowired
    ColorService colorService;

    Color color = new Color();

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    ObjectMapper mapper;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        color.setValue("Black");
        color = colorService.createOrUpdate(color);

        this.mvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @AfterEach
    public void cleanup() {
        colorRepository.deleteAll();
    }

    @Test
    void findAllColors() throws Exception {

        MvcResult mvcResult =
                this.mvc
                        .perform(get("/color")).andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();

        List<Color> result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        List<Color> expected = colorRepository.findAll();

        assertTrue(result.containsAll(expected));
    }


    @Test
    void findColorById() throws Exception {
        MvcResult mvcResult =
                this.mvc
                        .perform(get("/color/" + color.getId().toString())).andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();

        Color result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                Color.class);


        assertEquals(color, result);
    }

    @Test
    void create() throws Exception {
        Color newColor = new Color();
        newColor.setValue("Red");

        MvcResult mvcResult =
                this.mvc
                        .perform(post("/color")
                                .contentType("application/json")
                                .content(mapper.writeValueAsString(newColor)))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();

        Color result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                Color.class);

        Color expected = colorRepository.findById(result.getId()).orElse(null);

        assertEquals(result, expected);
    }

    @Test
    void deleteColor() throws Exception {

        MvcResult mvcResult =
                this.mvc
                        .perform(delete("/color/" + color.getId()))
                        .andExpect(status().isOk())
                        .andReturn();

        Color result = colorRepository.findById(color.getId()).orElse(null);
        assertNull(result);
    }

    @Test
    void createColorValidation() throws Exception {
        Color newColor = new Color();

        this.mvc
                .perform(post("/color")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(newColor)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void updateColorValidation() throws Exception {
        Color newColor = new Color();

        this.mvc
                .perform(put("/color")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(newColor)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    /*
    @Test
    void updateColorByNonExistingId() throws Exception {
        this.mvc
                .perform(put("/buyer/" + colorId)
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(color)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }
    */

    @Test
    void findColorByNonExistingId() throws Exception {
        this.mvc
                .perform(get("/buyer/" + colorId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void deleteBuyerByNonExistingId() throws Exception {
        this.mvc
                .perform(delete("/color/" + colorId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void updateColor() throws Exception {
        color.setValue("Update");

        MvcResult mvcResult =
                this.mvc
                        .perform(put("/color")
                                .contentType("application/json")
                                .content(mapper.writeValueAsString(color)))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();

        Color result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                Color.class);

        Color expected = colorRepository.findById(result.getId()).orElse(null);

        assertEquals("Update", expected.getValue());
        assertEquals(expected, result);
    }

}

