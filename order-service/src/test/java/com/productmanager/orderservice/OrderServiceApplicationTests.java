package com.productmanager.orderservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.productmanager.orderservice.dto.OrderLineItemsDTO;
import com.productmanager.orderservice.dto.OrderRequest;
import com.productmanager.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class OrderServiceApplicationTests {
    @Container
    static MySQLContainer mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.0-debian"))
            .withReuse(true);

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private OrderRepository orderRepository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.data.mysql.uri", mySQLContainer::getJdbcUrl);
    }

    @Test
    void shouldSaveOrder() throws Exception {
        int size = orderRepository.findAll().size();
        OrderRequest request = getOrderRequest();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
        Assertions.assertEquals(size + 1, orderRepository.findAll().size());
    }

    private OrderRequest getOrderRequest() {
        List<OrderLineItemsDTO> list = new ArrayList<>();
        list.add(OrderLineItemsDTO.builder()
                .price(BigDecimal.valueOf(1L))
                .skuCode("TestCode")
                .quantity(1)
                .build());
        return OrderRequest.builder()
                .orderLineItemsDTOList(list)
                .build();

    }

}
