package ru.liga.java.socialnetwork.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTOForRegistration {

    private String email;
    private String firstName;
    private String lastName;
}
