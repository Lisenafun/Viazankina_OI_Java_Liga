package service_order.domains;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Customer {

    private int id;
    private String name;
    private String email;
}
