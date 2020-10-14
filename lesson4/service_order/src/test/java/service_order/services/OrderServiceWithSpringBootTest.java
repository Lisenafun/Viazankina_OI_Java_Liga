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
    @DisplayName("Проверка корректности записи данных")
    public void testCreateOrderOK() {
        Order expectedOrder = new Order(1,"book", 1500,1);
        Mockito.when(orderDAO.addOrder(any())).thenReturn(expectedOrder);
        Order actualOrder = orderService.createOrder(new Order("book", 1));
        assertEquals(expectedOrder, actualOrder);
        Mockito.verify(orderDAO).addOrder(any());
    }
}
