package service_order.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomerDAO {

    /**
     * Получение id текущего покупателя
     *
     * @return id Номер id текущего покупателя
     */
    public int getCurrentCustomerId() {
        return 1;
    }
}
