package ru.liga.java.socialnetwork.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.java.socialnetwork.domains.Friendship;
import ru.liga.java.socialnetwork.domains.User;
import ru.liga.java.socialnetwork.dto.UserEditDto;
import ru.liga.java.socialnetwork.repositories.FriendshipRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendshipService {

    private final UserService userService;

    private final FriendshipRepository friendshipRepository;

    public Integer addFriend(Integer userId, Integer friendId) {
        User owner = userService.validById(userId);
        User friend = userService.validById(friendId);
        Optional<Friendship> friendship = friendshipRepository.findByOwnerAndFriend(owner, friend);
        if(friendship.isPresent()) {
            throw new RuntimeException("Дружба между данными пользователями уже существует.");
        }
        Friendship friendshipUF = new Friendship(owner, friend);
        Friendship friendshipFU = new Friendship(friend, owner);
        friendshipRepository.save(friendshipFU);
        friendshipUF = friendshipRepository.save(friendshipUF);
        return friendshipUF.getId();
    }

    public void deleteFriend(Integer userId, Integer friendId) {
        User owner = userService.validById(userId);
        User friend = userService.validById(friendId);
        friendshipRepository.deleteByOwnerAndFriend(owner, friend);
        friendshipRepository.deleteByOwnerAndFriend(friend, owner);
    }

    public List<UserEditDto> getFriendList(Integer userId) {
        User owner = userService.validById(userId);
        return friendshipRepository.findByOwner(owner)
                .stream().map(userService::convertToEditDto)
                .collect(Collectors.toList());
    }
}
