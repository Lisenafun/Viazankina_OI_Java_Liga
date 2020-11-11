package ru.liga.java.socialnetwork.services.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * Базовая спецификация
 */
public class BaseSpecification {

    /**
     * Поиск по вхождению
     *
     * @param column колонка таблицы сущности T
     * @param value значения для поиска
     * @return спецификация
     */
    public static <T> Specification<T> like(final String column, final String value) {
        return StringUtils.isEmpty(column) || StringUtils.isEmpty(value)
                ? null
                : (root, query, cb) ->
                cb.like(cb.lower(root.get(column)), "%" + value.toLowerCase() + "%");
    }

    /**
     * Поиск по эквивалентности поля
     *
     * @param column колонка таблицы сущности T
     * @param flag   значение поля
     * @return спецификация
     */
    public static <T> Specification<T> equal(final String column, final Object flag) {
        return StringUtils.isEmpty(column) || ObjectUtils.isEmpty(flag)
                ? null
                : (root, query, cb) -> cb.equal(root.get(column), flag);
    }
}
