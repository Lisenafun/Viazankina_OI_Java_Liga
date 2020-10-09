package service_order.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import service_order.domains.Order;
import service_order.services.OrderService;

@RestController
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private final OrderService orderService;

    @PostMapping("/orders/")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.createOrder(order));
    }
}
