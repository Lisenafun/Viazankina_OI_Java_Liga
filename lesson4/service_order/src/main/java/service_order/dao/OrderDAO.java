package service_order.dao;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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

//    public Order addOrder(Order order){
//        String sql = "INSERT INTO Orders (ID, NAME, PRICE, CUSTOMER_ID)" + "VALUES (:id, :name, :price, :customer_id)";
//        NamedParameterJdbcTemplate namedTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
//        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
//        sqlParameterSource.addValue("name", order.getName());
//        sqlParameterSource.addValue("price", order.getPrice());
//        sqlParameterSource.addValue("customer_id", customerDAO.getCurrentCustomerId());
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        namedTemplate.update(sql, sqlParameterSource, keyHolder);
//        order.setId((int)keyHolder.getKey());
//        order.setCustomerId(customerDAO.getCurrentCustomerId());
//        return order;
//    }

    public int addOrder(Order order){
        String sql = "INSERT INTO Orders (NAME, PRICE, CUSTOMER_ID)" + "VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        order.setCustomerId(customerDAO.getCurrentCustomerId());
        jdbcTemplate.update( connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"ID"});
            ps.setString(1, order.getName());
            ps.setInt(2, order.getPrice());
            ps.setInt(3, order.getCustomerId());
            return ps;
        }, keyHolder);
        Number key = keyHolder.getKey();
        assert key != null;
        return key.intValue();
    }
}
