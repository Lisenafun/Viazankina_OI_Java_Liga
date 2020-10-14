package service_order.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service_order.dao.OrderDAO;
import service_order.domains.Order;

@Service ("orderService")
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    private final OrderDAO orderDAO;

    public Order createOrder(Order order) {
        if(validate(order)) {
            return orderDAO.addOrder(order);
        }else{
            return null;
        }
    }

    private boolean validate(Order order){
        return order.getPrice() > 0 && !order.getName().equals("");
    }
}
