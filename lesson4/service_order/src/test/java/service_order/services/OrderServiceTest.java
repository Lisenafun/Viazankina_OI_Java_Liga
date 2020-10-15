package service_order.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import service_order.dao.OrderDAO;
import service_order.domains.Order;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

public class OrderServiceTest {

    @Mock
    OrderDAO orderDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Проверка корректности работы метода")
    public void testCreateOrderOK() throws Exception {
        int expectedOrderId = 1;
        Mockito.when(orderDAO.addOrder(any())).thenReturn(expectedOrderId);
        OrderService orderService = new OrderService(orderDAO);
        int actualOrderId = orderService.createOrder(new Order("book", 1));
        assertEquals(expectedOrderId, actualOrderId);
        Mockito.verify(orderDAO).addOrder(any());
    }

    @Test
    @DisplayName("Проверка на получение exception, когда указанная цена < 0")
    public void testCreateOrderExceptionBadPrice() {
        Order orderFalse = new Order(anyString(), -15);
        OrderService orderService = new OrderService(orderDAO);
        Exception exception = assertThrows(Exception.class, () -> orderService.createOrder(orderFalse));
        String expectedMessage = "Некорректно заполненный заказ.";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Проверка на получение exception, когда передана пустая строка без символов.")
    public void testCreateOrderExceptionEmptyName() {
        Order orderFalse = new Order("", anyInt());
        OrderService orderService = new OrderService(orderDAO);
        Exception exception = assertThrows(Exception.class, () -> orderService.createOrder(orderFalse));
        String expectedMessage = "Некорректно заполненный заказ.";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }
}
