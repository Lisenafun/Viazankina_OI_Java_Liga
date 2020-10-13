package service_order.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import service_order.domains.Order;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OrderDAOTest {

//    @Mock
//    private JdbcTemplate jdbcTemplate;
//
//    @Mock
//    private CustomerDAO customerDAO;
//
//    @Mock
//    private KeyHolder keyHolder;

//    @InjectMocks
//    private OrderDAO orderDAO;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

    @Test
    public void testAddOrder(){
        Order order = new Order("book", 1500);
//        OrderDAO orderDAO = new OrderDAO(jdbcTemplate, customerDAO, keyHolder);
//        Mockito.when(orderDAO.addOrder(order)).thenReturn(order);
//        assertEquals();
    }
}
