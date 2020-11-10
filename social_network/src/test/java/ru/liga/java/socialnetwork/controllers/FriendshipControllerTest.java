package ru.liga.java.socialnetwork.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import ru.liga.java.socialnetwork.dto.UserEditDto;
import ru.liga.java.socialnetwork.services.FriendshipService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

public class FriendshipControllerTest {

    @Mock
    private FriendshipService friendshipService;

    private FriendshipController friendshipController;
    private UserEditDto expectedUserEditDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        friendshipController = new FriendshipController(friendshipService);
        expectedUserEditDto = new UserEditDto("fedorov@mail.ru", "Fedor", "Fedorov","Dancing", 15, "Male", "Moscow");
    }

    @Test
    @DisplayName("Работоспособность метода получения списка друзей")
    public void testGetFriendsOk(){
        List<UserEditDto> expectedUsers = new ArrayList<>();
        expectedUsers.add(expectedUserEditDto);
        Mockito.when(friendshipService.getFriendList(anyInt())).thenReturn(expectedUsers);
        ResponseEntity<List<UserEditDto>> answer = friendshipController.getFriends(any());
        assertEquals(expectedUsers, answer.getBody());
        assertEquals(200, answer.getStatusCodeValue());
        Mockito.verify(friendshipService, Mockito.times(1)).getFriendList(any());
    }

    @Test
    @DisplayName("Работоспособность метода добавления друга")
    public void testAddFriendOk() {
        Integer expectedId = 1;
        Mockito.when(friendshipService.addFriend(anyInt(), anyInt())).thenReturn(expectedId);
        ResponseEntity<String> answer = friendshipController.addFriend(anyInt(), anyInt());
        assertEquals(expectedId.toString(), answer.getBody());
        assertEquals(200, answer.getStatusCodeValue());
        Mockito.verify(friendshipService, Mockito.times(1)).addFriend(anyInt(), anyInt());
    }

    @Test
    @DisplayName("Работоспособность метода удаления друга")
    public void testDeleteFriendOk(){
        Mockito.doNothing().when(friendshipService).deleteFriend(anyInt(), anyInt());
        ResponseEntity<String> answer = friendshipController.deleteFriend(anyInt(), anyInt());
        assertEquals(204, answer.getStatusCodeValue());
        assertNull(answer.getBody());
        Mockito.verify(friendshipService, Mockito.times(1)).deleteFriend(anyInt(), anyInt());
    }

}
