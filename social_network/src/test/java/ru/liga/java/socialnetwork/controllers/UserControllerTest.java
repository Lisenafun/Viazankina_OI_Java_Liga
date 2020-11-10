package ru.liga.java.socialnetwork.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import ru.liga.java.socialnetwork.dto.UserEditDto;
import ru.liga.java.socialnetwork.services.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    private UserController userController;
    private UserEditDto expectedUserEditDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userController = new UserController(userService);

        expectedUserEditDto = new UserEditDto("fedorov@mail.ru", "Fedor", "Fedorov","Dancing", 15, "Male", "Moscow");

    }
    @Test
    @DisplayName("Работоспособность метода добавления пользователя")
    public void testAddOk(){
        Integer expectedOrderId = 1;
        Mockito.when(userService.add(any())).thenReturn(expectedOrderId);
        ResponseEntity<String> answer = userController.add(any());
        assertEquals(expectedOrderId.toString(), answer.getBody());
        Mockito.verify(userService, Mockito.times(1)).add(any());
    }

    @Test
    @DisplayName("Работоспособность метода получения пользователя")
    public void testGetByIdOk(){
        Mockito.when(userService.findOne(any())).thenReturn(expectedUserEditDto);
        ResponseEntity<UserEditDto> answer = userController.get(anyInt());
        assertEquals(expectedUserEditDto, answer.getBody());
        Mockito.verify(userService,Mockito.times(1)).findOne(anyInt());
    }

    @Test
    @DisplayName("Работоспособность метода получения списка пользователей")
    public void testGetWithFiltersOk(){
        List<UserEditDto> expectedUsers = new ArrayList<>();
        expectedUsers.add(expectedUserEditDto);
        Mockito.when(userService.findWithFilter(any())).thenReturn(expectedUsers);
        ResponseEntity<List<UserEditDto>> answer = userController.getUsersWithFilters(any());
        assertEquals(expectedUsers, answer.getBody());
        Mockito.verify(userService, Mockito.times(1)).findWithFilter(any());
    }

    @Test
    @DisplayName("Работоспособность метода изменения данных пользователя")
    public void testUpdateOk(){
        Integer userId = 1;
        Mockito.when(userService.update(any(), anyInt())).thenReturn(userId);
        ResponseEntity<String> answer = userController.update(any(), anyInt());
        assertEquals(userId.toString(), answer.getBody());
        Mockito.verify(userService, Mockito.times(1)).update(any(), anyInt());
    }

    @Test
    @DisplayName("Работоспособность метода удаления пользователя")
    public void testDeleteOk(){
        Mockito.doNothing().when(userService).delete(anyInt());
        ResponseEntity<String> answer = userController.delete(anyInt());
        assertEquals(204, answer.getStatusCodeValue());
        assertNull(answer.getBody());
        Mockito.verify(userService, Mockito.times(1)).delete(anyInt());
    }
}
