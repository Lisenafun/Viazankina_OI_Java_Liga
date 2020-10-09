package service_order.domains;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Customer {

    private long id;
    private String name;
    private String email;
}
