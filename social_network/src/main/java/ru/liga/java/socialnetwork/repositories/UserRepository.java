package ru.liga.java.socialnetwork.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.liga.java.socialnetwork.domains.User;

import java.util.Optional;

/**
 * Репозиторий Пользователя
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer>, JpaSpecificationExecutor<User> {

    /**
     * Метод для получения пользователя по адресу почты
     *
     * @param email Адрес почты
     * @return Optional Пользователя
     */
    Optional<User> findByEmail(String email);
}
