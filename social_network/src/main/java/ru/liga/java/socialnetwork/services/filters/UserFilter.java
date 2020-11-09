package ru.liga.java.socialnetwork.services.filters;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import ru.liga.java.socialnetwork.domains.User;
import ru.liga.java.socialnetwork.enums.Gender;
import ru.liga.java.socialnetwork.services.specifications.BaseSpecification;

@Getter
@Setter
public class UserFilter implements Filter<User>{

    private String firstNameLike;
    private String lastNameLike;
    private Integer ageEqual;
    private Gender genderEqual;
    private String townLike;

    @Override
    public Specification<User> toSpecification() {
        return Specification.where(BaseSpecification.<User>like("firstName", firstNameLike))
                .and(BaseSpecification.like("lastName", lastNameLike))
                .and(BaseSpecification.equal("age", ageEqual))
                .and(BaseSpecification.equal("gender", genderEqual))
                .and(BaseSpecification.like("town", townLike));
    }
}
