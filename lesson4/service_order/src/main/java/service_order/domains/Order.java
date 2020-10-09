package service_order.domains;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Order {

    private long id;
    private String name;
    private int price;
    private long customerId;
}
