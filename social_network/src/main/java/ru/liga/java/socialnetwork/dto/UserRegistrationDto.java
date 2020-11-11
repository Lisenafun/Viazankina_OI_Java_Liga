package ru.liga.java.socialnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto для регистрации пользователя
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDto {

    /**
     * Поле Адрес почты
     */
    private String email;

    /**
     * Поле Имя
     */
    private String firstName;

    /**
     * Поле Фамилия
     */
    private String lastName;
}
