package com.productmanager.orderservice.service;

import com.productmanager.orderservice.dto.OrderLineItemsDTO;
import com.productmanager.orderservice.dto.OrderRequest;
import com.productmanager.orderservice.model.Order;
import com.productmanager.orderservice.repository.OrderRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
public class OrderServiceUnitTests {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        orderService = new OrderService(orderRepository, null);
    }

    @Test
    public void saveOrder() {
        OrderRequest orderRequest = new OrderRequest();
        List<OrderLineItemsDTO> orderLineItemsList = new ArrayList<>();
        orderLineItemsList.add(new OrderLineItemsDTO(1L, "123", BigDecimal.TEN, 10));
        orderRequest.setOrderLineItemsDTOList(orderLineItemsList);

        // Capture the order that's passed to orderRepository.save()
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        List<Order> expectedOrders = new ArrayList<>();
        when(orderRepository.findAll()).thenReturn(expectedOrders);
        orderService.placeOrder(orderRequest);

        // Verify that orderRepository.save() was called with the expected order
        verify(orderRepository).save(orderCaptor.capture());

        // Assert that the saved order matches your expectations
        Order savedOrder = orderCaptor.getValue();
        expectedOrders.add(savedOrder);

        assertEquals(orderRequest.getOrderLineItemsDTOList().size(), savedOrder.getOrderLineItemsList().size());

        // Now, assert that there is one item in the repository
        assertEquals(1, orderRepository.findAll().size());
    }
}