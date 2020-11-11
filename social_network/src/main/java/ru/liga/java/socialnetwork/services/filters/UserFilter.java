package ru.liga.java.socialnetwork.services.filters;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import ru.liga.java.socialnetwork.domains.User;
import ru.liga.java.socialnetwork.enums.Gender;
import ru.liga.java.socialnetwork.services.specifications.BaseSpecification;

/**
 * Фильтр для пользователя
 */
@Getter
@Setter
public class UserFilter implements Filter<User>{

    /**
     * Имя пользователя
     */
    private String firstNameLike;

    /**
     * Фамилия пользователя
     */
    private String lastNameLike;

    /**
     * Возраст пользователя
     */
    private Integer ageEqual;

    /**
     * Пол пользователя
     */
    private Gender genderEqual;

    /**
     * Город, где проживает пользователь
     */
    private String townLike;

    /**
     * Метод для определения спецификации по фильтрам
     *
     * @return Спецификация по пользователю
     */
    @Override
    public Specification<User> toSpecification() {
        return Specification.where(BaseSpecification.<User>like("firstName", firstNameLike))
                .and(BaseSpecification.like("lastName", lastNameLike))
                .and(BaseSpecification.equal("age", ageEqual))
                .and(BaseSpecification.equal("gender", genderEqual))
                .and(BaseSpecification.like("town", townLike));
    }
}
