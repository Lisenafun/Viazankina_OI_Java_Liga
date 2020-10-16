package service_order.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import service_order.domains.Order;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.PreparedStatement;

@RequiredArgsConstructor
@Component
public class OrderDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private CustomerDAO customerDAO;

    @PostConstruct
    private void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int addOrder(Order order){
        String sql = "INSERT INTO Orders (NAME, PRICE, CUSTOMER_ID)" + "VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update( connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"ID"});
            ps.setString(1, order.getName());
            ps.setInt(2, order.getPrice());
            ps.setInt(3, customerDAO.getCurrentCustomerId());
            return ps;
        }, keyHolder);
        Number key = keyHolder.getKey();
        return key.intValue();
    }
}
