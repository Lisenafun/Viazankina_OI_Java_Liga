package service_order.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import service_order.domains.Order;
import service_order.services.OrderService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;

public class OrderControllerTest {

    private OrderController orderController;

    @Mock
    OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderController = new OrderController(orderService);
    }

    @Test
    @DisplayName("Проверяем работу метода в контроллере")
    public void testCreateOrderOk() throws Exception {
        int expectedOrderId = 1;
        Mockito.when(orderService.createOrder(any())).thenReturn(expectedOrderId);
        ResponseEntity<?> answer = orderController.createOrder(new Order("book", 1, 1));
        assertEquals(expectedOrderId, answer.getBody());
        Mockito.verify(orderService).createOrder(any());
    }
}
