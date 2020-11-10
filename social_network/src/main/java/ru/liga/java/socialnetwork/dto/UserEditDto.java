package ru.liga.java.socialnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEditDto {

    private String email;
    private String firstName;
    private String lastName;
    private String interests;
    private Integer age;
    private String gender;
    private String town;
}
