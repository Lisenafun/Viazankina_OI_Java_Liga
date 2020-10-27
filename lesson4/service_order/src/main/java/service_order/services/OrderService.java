package service_order.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import service_order.dao.OrderDAO;
import service_order.domains.Order;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderDAO orderDAO;

    /**
     * Проверяем, прошел ли заказ валидацию и сохраняем заказ в БД
     *
     * @param order Заказ
     * @return id Заказа или Exception
     * @throws Exception Если не пройдена вылидация
     */
    public Integer createOrder(Order order) throws Exception {
        if(validate(order)) {
            throw new Exception("Некорректно заполненный заказ.");
        }
        return orderDAO.addOrder(order);
    }

    /**
     * Проверка получения корректной цены и не пустого поля имени заказа
     *
     * @param order Заказ
     * @return true, если цена <= 0 и имя = null или пустое поле, иначе false
     */
    private boolean validate(Order order) {
        return order.getPrice() <= 0 || StringUtils.isEmpty(order.getName());
    }
}
