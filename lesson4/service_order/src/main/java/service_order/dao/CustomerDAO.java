package service_order.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomerDAO {

    private final JdbcTemplate jdbcTemplate;

    public long getCurrentCustomerId(){
        return 1;
    }
}
