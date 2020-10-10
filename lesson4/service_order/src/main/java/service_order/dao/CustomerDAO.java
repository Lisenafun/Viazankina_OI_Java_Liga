package service_order.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomerDAO {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public int getCurrentCustomerId(){
        return 1;
    }
}
