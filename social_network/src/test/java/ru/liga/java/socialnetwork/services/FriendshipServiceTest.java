package ru.liga.java.socialnetwork.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.liga.java.socialnetwork.domains.Friendship;
import ru.liga.java.socialnetwork.domains.User;
import ru.liga.java.socialnetwork.dto.UserEditDto;
import ru.liga.java.socialnetwork.repositories.FriendshipRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

public class FriendshipServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private FriendshipRepository friendshipRepository;

    private FriendshipService friendshipService;

    private User user;
    private User friend;
    private Friendship friendship1;
    private Friendship friendship2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        friendshipService = new FriendshipService(userService, friendshipRepository);

        user = new User("blinov@mail.ru", "Ivan", "Blinov");
        friend = new User("blinova@mail.ru", "Marina", "Blinova");
        user.setId(1);
        friend.setId(2);
        friendship1 = new Friendship(user, friend);
        friendship1.setId(1);
        friendship2 = new Friendship(friend, user);
        friendship2.setId(2);
    }

    @Test
    @DisplayName("Работоспособность метода добавления друга")
    public void testAddFriendOk(){
        Mockito.when(friendshipRepository.findByOwnerAndFriend(any(), any())).thenReturn(Optional.empty());
        Mockito.when(friendshipRepository.save(any())).thenReturn(friendship2);
        Mockito.when(friendshipRepository.save(any())).thenReturn(friendship1);
        Integer actualId = friendshipService.addFriend(user.getId(), friend.getId());
        Integer expectedId = 1;
        assertEquals(expectedId, actualId);
        Mockito.verify(friendshipRepository, Mockito.times(1)).findByOwnerAndFriend(any(),any());
        Mockito.verify(friendshipRepository, Mockito.times(2)).save(any());
    }

    @Test
    @DisplayName("Работоспособность метода удаления друга")
    public void testDeleteFriendOk(){
        friendshipService.deleteFriend(user.getId(), friend.getId());
        assertEquals(Optional.empty(), friendshipRepository.findByOwnerAndFriend(user, friend));
        Mockito.verify(friendshipRepository, Mockito.times(2)).deleteByOwnerAndFriend(any(),any());
    }

    @Test
    @DisplayName("Работоспособность метода получения списка друзей")
    public void testGetFriendListOk(){
        List<User> friends = new ArrayList<>();
        friends.add(friend);
        Mockito.when(friendshipRepository.findByOwner(any())).thenReturn(friends);
        List<UserEditDto> expectedFriends = new ArrayList<>();
        friends.forEach(friend -> expectedFriends.add(userService.convertToEditDto(friend)));
        List<UserEditDto> actualFriends = friendshipService.getFriendList(user.getId());
        assertEquals(expectedFriends, actualFriends);
        Mockito.verify(friendshipRepository, Mockito.times(1)).findByOwner(any());
    }
}
