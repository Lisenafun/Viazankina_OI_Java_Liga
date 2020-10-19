package service_order.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service_order.dao.OrderDAO;
import service_order.domains.Order;

@Service("orderService")
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    private final OrderDAO orderDAO;

    /**
     * Проверяем, прошел ли заказ валидацию и сохраняем заказ в БД
     *
     * @param order Заказ
     * @return id Заказа или Exception
     * @throws Exception Если не пройдена вылидация
     */
    public int createOrder(Order order) throws Exception {
        if(!validate(order)) throw new Exception("Некорректно заполненный заказ.");
        return orderDAO.addOrder(order);
    }

    /**
     * Проверка получения корректной цены и не пустого поля имени заказа
     *
     * @param order Заказ
     * @return true, если цена > 0 и имя = не пустое поле, иначе false
     */
    private boolean validate(Order order) {
        return order.getPrice() > 0 && !order.getName().equals("");
    }
}
