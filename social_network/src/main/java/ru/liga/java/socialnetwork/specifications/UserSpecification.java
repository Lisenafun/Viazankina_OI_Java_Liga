package ru.liga.java.socialnetwork.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.liga.java.socialnetwork.domains.User;
import ru.liga.java.socialnetwork.enums.Gender;

import javax.persistence.criteria.*;

public class UserSpecification {

    public static Specification<User> getUserByFirstNameSpec(String firstName){
        return (Specification<User>) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("firstName"), firstName);
    }

    public static Specification<User> getUserByLastNameSpec(String lastName){
        return (Specification<User>) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("lastName"), lastName);
    }

    public static Specification<User> getUserByAgeSpec(Integer age){
        return (Specification<User>) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("age"), age);
    }

    public static Specification<User> getUserByGenderSpec(Gender gender){
        return (Specification<User>) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("gender"), gender);
    }

    public static Specification<User> getUserByTownSpec(String town){
        return (Specification<User>) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("town"), town);
    }
}
