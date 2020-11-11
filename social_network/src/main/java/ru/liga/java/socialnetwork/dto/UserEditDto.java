package ru.liga.java.socialnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto для редактирования и вывода данных пользователя
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEditDto {

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

    /**
     * Поле Интересы
     */
    private String interests;

    /**
     * Поле возраст
     */
    private Integer age;


    /**
     * Поле пол
     */
    private String gender;

    /**
     * Поле Город
     */
    private String town;
}
