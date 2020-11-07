package ru.liga.java.socialnetwork.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.liga.java.socialnetwork.domains.Friendship;
import ru.liga.java.socialnetwork.domains.User;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface FriendshipRepository extends CrudRepository<Friendship, Integer>, JpaSpecificationExecutor<Friendship> {

    @Query("SELECT f.friend FROM Friendship f WHERE f.owner = ?1")
    List<User> findByOwner(User owner);

    @Transactional
    void deleteByOwnerAndFriend(User owner, User friend);
}
