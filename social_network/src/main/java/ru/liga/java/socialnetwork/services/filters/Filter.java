package ru.liga.java.socialnetwork.services.filters;

import org.springframework.data.jpa.domain.Specification;

@FunctionalInterface
public interface Filter<T> {

    /**
     * Определение спецификации для фильтра
     * @return спецификация
     */
    Specification<T> toSpecification();
}
