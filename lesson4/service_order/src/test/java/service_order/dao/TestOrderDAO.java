package service_order.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import service_order.domains.Order;


public class TestOrderDAO {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private CustomerDAO customerDAO;

    private OrderDAO orderDAO;

    @Test
    public void addOrderTest(){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int id = (int) keyHolder.getKey();
        Order order = new Order(id,"book", 1500, 1);
        orderDAO.addOrder(order);
    }
}
