package com.productmanager.inventoryservice.service;

import com.productmanager.inventoryservice.model.Inventory;
import com.productmanager.inventoryservice.repository.InventoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
public class InventoryServiceUnitTests {

    @Mock
    InventoryRepository inventoryRepository;

    @InjectMocks
    InventoryService inventoryService;

    @BeforeEach
    public void init(){
        inventoryService = new InventoryService(inventoryRepository);
    }



}
