package ru.liga.java.socialnetwork.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTOForUser {

    private String email;
    private String firstName;
    private String lastName;
    private String interests;
    private Integer age;
    private String gender;
    private String town;
}
