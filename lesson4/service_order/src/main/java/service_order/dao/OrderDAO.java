package service_order.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import service_order.domains.Order;

@RequiredArgsConstructor
@Component
public class OrderDAO {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private CustomerDAO customerDAO;

    public Order addOrder(Order order){
        String sql = "INSERT INTO Orders (NAME, PRICE, CUSTOMER_ID)" + "VALUES (:name, :price, :customer_id)";
        NamedParameterJdbcTemplate namedTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("name", order.getName());
        sqlParameterSource.addValue("price", order.getPrice());
        sqlParameterSource.addValue("customer_id", customerDAO.getCurrentCustomerId());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedTemplate.update(sql, sqlParameterSource, keyHolder);
        order.setId((int)keyHolder.getKey());
        order.setCustomerId(customerDAO.getCurrentCustomerId());
        return order;
    }
}
