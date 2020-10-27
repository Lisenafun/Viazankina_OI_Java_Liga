package service_order.domains;

import lombok.*;

import java.io.Serializable;

@Data
public class Order implements Serializable {

    private Integer id;
    private String name;
    private int price;
    private int customerId;

    public Order(String name, Integer price, Integer customerId) {
        this.name = name;
        this.price = price;
        this.customerId = customerId;
    }
}
