package com.example.testcaseskypro.controller;

import com.example.testcaseskypro.entity.Socks;
import com.example.testcaseskypro.repository.SocksRepository;
import com.example.testcaseskypro.service.SocksService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class SocksControllerTest {

    public static final int ID = 1;
    public static final String COLOR = "orange";
    public static final String WRONG_COLOR = "green";
    public static final int COTTON_PART = 90;
    public static final int QUANTITY = 1000;
    public static final int NEW_QUANTITY = 500;
    public static final int QUANTITY_FOR_FAILING_SUBTRACTION = 50000;
    public static final int COTTON_PART_LESS_THAN_0 = -5;
    public static final int COTTON_PART_MORE_THAN_100 = 109;

    @MockBean
    private SocksRepository socksRepository;
    @SpyBean
    private SocksService socksService;
    @InjectMocks
    private SocksController socksController;
    @Autowired
    MockMvc mockMvc;

    @Test
    void addSocksTest() throws Exception {
        Socks socks = new Socks();
        socks.setId(ID);
        socks.setColor(COLOR);
        socks.setCottonPart(COTTON_PART);
        socks.setQuantity(QUANTITY);

        JSONObject addedObject = new JSONObject();
        addedObject.put("color", COLOR);
        addedObject.put("cottonPart", COTTON_PART);
        addedObject.put("quantity", NEW_QUANTITY);

        when(socksRepository.findByColorAndCottonPart(eq(COLOR), eq(COTTON_PART))).thenReturn(Optional.of(socks));
        when(socksRepository.save(any(Socks.class))).thenAnswer(i -> i.getArgument(0));

        mockMvc.perform(buildMockMvcIncomeMethod(addedObject))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.color").value(COLOR))
                .andExpect(jsonPath("$.cottonPart").value(COTTON_PART))
                .andExpect(jsonPath("$.quantity").value(QUANTITY + NEW_QUANTITY));

        addedObject.put("cottonPart", COTTON_PART_LESS_THAN_0);
        mockMvc.perform(buildMockMvcIncomeMethod(addedObject))
                .andExpect(status().isBadRequest());


        addedObject.put("cottonPart", COTTON_PART_MORE_THAN_100);
        mockMvc.perform(buildMockMvcIncomeMethod(addedObject))
                .andExpect(status().isBadRequest());

        addedObject.put("quantity", NEW_QUANTITY);
        addedObject.put("color", "");
        mockMvc.perform(buildMockMvcIncomeMethod(addedObject))
                .andExpect(status().isBadRequest());
    }

    public RequestBuilder buildMockMvcIncomeMethod(JSONObject income) {
        return MockMvcRequestBuilders
                .post("/api/socks/income")
                .content(income.toString())
                .contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    void subtractSocksTest() throws Exception {
        Socks socks = new Socks();
        socks.setId(ID);
        socks.setColor(COLOR);
        socks.setCottonPart(COTTON_PART);
        socks.setQuantity(QUANTITY);

        JSONObject subtractedObject = new JSONObject();
        subtractedObject.put("color", COLOR);
        subtractedObject.put("cottonPart", COTTON_PART);
        subtractedObject.put("quantity", NEW_QUANTITY);

        when(socksRepository.findByColorAndCottonPart(eq(COLOR), eq(COTTON_PART))).thenReturn(Optional.of(socks));
        when(socksRepository.save(any(Socks.class))).thenAnswer(i -> i.getArgument(0));

        mockMvc.perform(buildMockMvcOutcomeMethod(subtractedObject))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.color").value(COLOR))
                .andExpect(jsonPath("$.cottonPart").value(COTTON_PART))
                .andExpect(jsonPath("$.quantity").value(QUANTITY - NEW_QUANTITY));

        subtractedObject.put("cottonPart", COTTON_PART_LESS_THAN_0);
        mockMvc.perform(buildMockMvcIncomeMethod(subtractedObject))
                .andExpect(status().isBadRequest());


        subtractedObject.put("cottonPart", COTTON_PART_MORE_THAN_100);
        mockMvc.perform(buildMockMvcIncomeMethod(subtractedObject))
                .andExpect(status().isBadRequest());

        subtractedObject.put("quantity", NEW_QUANTITY);
        subtractedObject.put("color", "");
        mockMvc.perform(buildMockMvcIncomeMethod(subtractedObject))
                .andExpect(status().isBadRequest());

        subtractedObject.put("color", WRONG_COLOR);
        mockMvc.perform(buildMockMvcIncomeMethod(subtractedObject))
                .andExpect(status().isBadRequest());

        subtractedObject.put("color", COLOR);
        subtractedObject.put("quantity", QUANTITY_FOR_FAILING_SUBTRACTION);
        mockMvc.perform(buildMockMvcIncomeMethod(subtractedObject))
                .andExpect(status().isBadRequest());
    }

    public RequestBuilder buildMockMvcOutcomeMethod(JSONObject income) {
        return MockMvcRequestBuilders
                .post("/api/socks/outcome")
                .content(income.toString())
                .contentType(MediaType.APPLICATION_JSON);
    }
}
