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

    @Mock
    OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Проверяем работу метода в контроллере")
    public void testCreateOrderOk() throws Exception {
        int expectedOrderId = 1;
        Mockito.when(orderService.createOrder(any())).thenReturn(expectedOrderId);
        OrderController orderController = new OrderController(orderService);
        ResponseEntity<?> answer = orderController.createOrder(new Order("book", 1));
        assertEquals(expectedOrderId, answer.getBody());
        Mockito.verify(orderService).createOrder(any());
    }

    @Test()
    @DisplayName("Проверяем на получение Exception, если указана цена < 0")
    public void testCreateOrderExceptionBadPrice() {
        Order orderFalse = new Order(anyString(), -15);
        OrderController orderController = new OrderController(orderService);
        try {
            orderController.createOrder(orderFalse);
        } catch(Exception e) {
            assertEquals("Некорректно заполненный заказ.", e.getMessage());
        }
    }

    @Test()
    @DisplayName("Проверяем на получение Exception, если указана пустая строка в имени")
    public void testCreateOrderExceptionEmptyName() {
        Order orderFalse = new Order("", anyInt());
        OrderController orderController = new OrderController(orderService);
        try {
            orderController.createOrder(orderFalse);
        } catch(Exception e) {
            assertEquals("Некорректно заполненный заказ.", e.getMessage());
        }
    }
}
