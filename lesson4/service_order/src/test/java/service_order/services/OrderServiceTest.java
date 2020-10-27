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

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderService(orderDAO);
    }

    @Test
    @DisplayName("Проверка корректности работы метода")
    public void testCreateOrderOK() throws Exception {
        int expectedOrderId = 1;
        Mockito.when(orderDAO.addOrder(any())).thenReturn(expectedOrderId);
        int actualOrderId = orderService.createOrder(new Order("book", 1, 1));
        assertEquals(expectedOrderId, actualOrderId);
        Mockito.verify(orderDAO).addOrder(any());
    }

    @Test
    @DisplayName("Проверка получения exception, когда указанная цена < 0")
    public void testCreateOrderExceptionBadPrice() {
        Order orderFalse = new Order(anyString(), -15, anyInt());
        Exception exception = assertThrows(Exception.class, () -> orderService.createOrder(orderFalse));
        String expectedMessage = "Некорректно заполненный заказ.";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Проверка получения exception, когда передана пустая строка без символов.")
    public void testCreateOrderExceptionEmptyName() {
        Order orderFalse = new Order("", anyInt(), anyInt());
        Exception exception = assertThrows(Exception.class, () -> orderService.createOrder(orderFalse));
        String expectedMessage = "Некорректно заполненный заказ.";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }
}
