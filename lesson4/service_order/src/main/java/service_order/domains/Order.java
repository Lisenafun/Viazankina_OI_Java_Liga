package service_order.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {

    private int id;
    private String name;
    private int price;
    private int customerId;

    public Order(String name, int price){
        this.name = name;
        this.price = price;
    }
}
