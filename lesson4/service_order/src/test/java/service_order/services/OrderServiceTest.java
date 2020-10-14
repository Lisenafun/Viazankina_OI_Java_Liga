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
    @DisplayName("Проверка корректности записи данных")
    public void testCreateOrderOK() {
        Order expectedOrder = new Order(1, "book", 1500, 1);
        Mockito.when(orderDAO.addOrder(any())).thenReturn(expectedOrder);
        OrderService orderService = new OrderService(orderDAO);
        Order actualOrder = orderService.createOrder(new Order("book", 1));
        assertEquals(expectedOrder, actualOrder);
        Mockito.verify(orderDAO).addOrder(any());
    }

    @Test
    @DisplayName("Проверка на получение null при некорректном заказе")
    public void testCreateOrderFalse() {
        Order orderFalse = new Order(anyString(), -15);
        OrderService orderService = new OrderService(orderDAO);
        assertNull(orderService.createOrder(orderFalse));
    }
}
