package service_order.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import service_order.dao.OrderDAO;
import service_order.domains.Order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class OrderServiceWithSpringBootTest {

    @Autowired
    OrderService orderService;

    @MockBean
    OrderDAO orderDAO;

    @Test
    @DisplayName("Проверка корректности работы метода")
    public void testCreateOrderOK() throws Exception {
        int expectedOrderId = 1;
        Mockito.when(orderDAO.addOrder(any())).thenReturn(expectedOrderId);
        int actualOrderId = orderService.createOrder(new Order("book", 1));
        assertEquals(expectedOrderId, actualOrderId);
        Mockito.verify(orderDAO).addOrder(any());
    }
}
