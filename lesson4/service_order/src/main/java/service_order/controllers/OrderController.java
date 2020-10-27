package service_order.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service_order.domains.Order;
import service_order.services.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("orders")
public class OrderController {

    private final OrderService orderService;

    /**
     * Контроллер для создания заказа с параметрами и внесения его в БД
     *
     * @param order Заказ
     * @return Статус 200 и order.id при успешном выполнении, иначе статус 400 и сообщение об ошибке
     */
    @PostMapping
    public ResponseEntity<?> createOrder(Order order) {
        Integer orderId;
        try {
            orderId = orderService.createOrder(order);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(orderId);
    }
}
