package service_order.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.test.util.ReflectionTestUtils;
import service_order.domains.Order;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    @DisplayName("Проверяем работу метода в классе OrderDAO")
    public void testAddOrderOk() {
        Order order = new Order("book", 1500);
        order.setCustomerId(customerDAO.getCurrentCustomerId());
        OrderDAO orderDAO = new OrderDAO();
        ReflectionTestUtils.setField(orderDAO, "jdbcTemplate", jdbcTemplate);
        Mockito.when(jdbcTemplate.update(Mockito.any(PreparedStatementCreator.class), Mockito.any(GeneratedKeyHolder.class))).thenAnswer((Answer<?>) invocation -> {
            Object[] args = invocation.getArguments();
            Map<String, Object> keyMap = new HashMap<>();
            keyMap.put("", 1);
            ((GeneratedKeyHolder) args[1]).getKeyList().add(keyMap);
            return null;
        });
        assertEquals(1, orderDAO.addOrder(order));
    }
}
