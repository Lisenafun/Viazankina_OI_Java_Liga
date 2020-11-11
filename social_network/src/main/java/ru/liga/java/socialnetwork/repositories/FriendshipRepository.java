package ru.liga.java.socialnetwork.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.liga.java.socialnetwork.domains.Friendship;
import ru.liga.java.socialnetwork.domains.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Репозиторий сущности Friendship
 */
@Repository
public interface FriendshipRepository extends CrudRepository<Friendship, Integer>, JpaSpecificationExecutor<Friendship> {

    /**
     * Метод для получения списка друзей
     *
     * @param owner Пользователь, который дружит("владелец дружбы")
     * @return Список друзей
     */
    @Query("SELECT f.friend FROM Friendship f WHERE f.owner = ?1")
    List<User> findByOwner(User owner);

    /**
     * Метод для получения дружбы между двумя пользователя
     *
     * @param owner Пользователь - "владелец дружбы"
     * @param friend Пользователь - друг
     * @return Optional дружбы
     */
    Optional<Friendship> findByOwnerAndFriend(User owner, User friend);

    /**
     * Метод для удаления дружбы между двумя пользователями
     *
     * @param owner Пользователь - "владелец дружбы"
     * @param friend Пользователь - друг
     */
    @Transactional
    void deleteByOwnerAndFriend(User owner, User friend);
}
