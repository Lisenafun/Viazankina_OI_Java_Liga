package service_order.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import service_order.dao.OrderDAO;
import service_order.domains.Order;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Mock
    OrderDAO orderDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Проверка корректности записи данных")
    public void testCreateOrderOK() throws Exception {
        Mockito.when(orderDAO.addOrder(any())).thenReturn(new Order(1,"book", 1500, 1));
        Order order = new Order(1,"book", 1500,1);
        assertEquals(order, orderDAO.addOrder(any()));
    }
}
