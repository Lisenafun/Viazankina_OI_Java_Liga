package service_order.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import service_order.domains.Order;

import java.sql.PreparedStatement;

@Repository
@RequiredArgsConstructor
public class OrderDAO {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Сохранение заказ в базе данных
     *
     * @param order Заказ
     * @return id Номер id, сохраненного заказа
     */
    public Integer addOrder(Order order) {
        String sql = "INSERT INTO Orders (NAME, PRICE, CUSTOMER_ID)" + "VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"ID"});
            ps.setString(1, order.getName());
            ps.setInt(2, order.getPrice());
            ps.setInt(3, order.getCustomerId());
            return ps;
        }, keyHolder);
        Number key = keyHolder.getKey();
        return key != null ? key.intValue() : null;
    }
}
