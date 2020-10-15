package service_order.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.util.ReflectionTestUtils;
import service_order.domains.Order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;


public class OrderDAOTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private CustomerDAO customerDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddOrderOk(){
        Order order = new Order("book", 1500);
//        ReflectionTestUtils.setField(orderDAO, "jdbcTemplate", jdbcTemplate);
        Mockito.when(customerDAO.getCurrentCustomerId()).thenReturn(1).thenReturn(jdbcTemplate.update(anyString())).thenReturn(1);
//        Mockito.when(jdbcTemplate.update(anyString())).thenReturn(1);
        OrderDAO orderDAO = new OrderDAO();
        order.setCustomerId(customerDAO.getCurrentCustomerId());
        assertEquals(1, orderDAO.addOrder(order));
    }
}
