package com.productmanager.inventoryservice.controller;

import com.productmanager.inventoryservice.repository.InventoryRepository;
import com.productmanager.inventoryservice.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class InventoryControllerUnitTests {

    private MockMvc mockMvc;

    @Mock
    InventoryService inventoryService;

    @InjectMocks
    InventoryController inventoryController;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(inventoryController).build();
    }

    @Test
    void testIfPresent() throws Exception {
        String skuCode = "test";
        when(inventoryService.isInStock(Collections.singletonList(skuCode))).thenReturn();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/inventory/" + skuCode)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
