package ru.liga.java.socialnetwork.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import ru.liga.java.socialnetwork.domains.User;
import ru.liga.java.socialnetwork.dto.UserEditDto;
import ru.liga.java.socialnetwork.dto.UserRegistrationDto;
import ru.liga.java.socialnetwork.enums.Gender;
import ru.liga.java.socialnetwork.repositories.FriendshipRepository;
import ru.liga.java.socialnetwork.repositories.UserRepository;
import ru.liga.java.socialnetwork.services.filters.UserFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private FriendshipRepository friendshipRepository;

    @Mock
    private ModelMapper modelMapper;

    private UserService userService;

    private User user;
    private UserRegistrationDto userDto;
    private UserEditDto userEditDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, friendshipRepository, modelMapper);

        user = new User("blinov@mail.ru", "Ivan", "Blinov");
        user.setId(1);
        userDto = new UserRegistrationDto("ivanov@mail.ru", "Ivan", "Ivanov");
        userEditDto = new UserEditDto("fedorov@mail.ru", "Fedor", "Fedorov","Dancing", 15, "Male", "Moscow");
    }

    @Test
    @DisplayName("Работоспособность метода поиска пользователя по id")
    public void testFindOneOk(){
        UserEditDto userEditDtoExpected = userService.convertToEditDto(user);
        Mockito.when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        UserEditDto userEditDtoActual = userService.findOne(1);
        assertEquals(userEditDtoExpected, userEditDtoActual);
        Mockito.verify(userRepository, Mockito.times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("Работоспособность метода поиска пользователей с фильтром")
    public void testFindWithFilterOk(){
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(new User("blinova@mail.ru", "Marina", "Blinova"));
        List<UserEditDto> usersDtoExpected = users.stream()
                .map(userService::convertToEditDto)
                .collect(Collectors.toList());
        Mockito.when(userRepository.findAll(any())).thenReturn(users);
        List<UserEditDto> usersDtoActual = userService.findWithFilter(new UserFilter());
        assertEquals(2, usersDtoActual.size());
        assertEquals(usersDtoExpected, usersDtoActual);
        Mockito.verify(userRepository, Mockito.times(1)).findAll(any());
    }

    @Test
    @DisplayName("Работоспособность метода добавления пользователя")
    public void testAddOk(){
        Integer expectedUserId = 1;
        Mockito.when(modelMapper.map(userDto, User.class)).thenReturn(user);
        Mockito.when(userRepository.save(any())).thenReturn(user).thenReturn(user.getId());
        Integer actualUserId = userService.add(userDto);
        assertEquals(expectedUserId, actualUserId);
        Mockito.verify(modelMapper, Mockito.times(1)).map(userDto, User.class);
        Mockito.verify(userRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("Рабоспособность метода внесения изменений у пользователя")
    public void testUpdateOk(){
        User userNew = userService.convertFromEditDto(userEditDto, new User());
        Mockito.when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(any())).thenReturn(userNew).thenReturn(userNew.getId());
        userService.update(userEditDto, user.getId());
        String actualName = user.getFirstName();
        String expectedName = userEditDto.getFirstName();
        assertEquals(expectedName, actualName);
        Mockito.verify(userRepository, Mockito.times(1)).findById(anyInt());
        Mockito.verify(userRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("Рабоспособность метода по удалению пользователя")
    public void testDeleteOk(){
        Mockito.when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        userService.delete(user.getId());
        assertNull(userService.findOne(1));
        Mockito.verify(userRepository,Mockito.times(1)).deleteById(anyInt());
        Mockito.verify(userRepository, Mockito.times(2)).findById(anyInt());
    }

    @Test
    @DisplayName("Рабоспособность метода проверки наличия пользователя по id")
    public void testValidByIdOk(){
        Mockito.when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        User userNew = userService.validById(1);
        assertEquals(user, userNew);
        Mockito.verify(userRepository, Mockito.times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("Получение Exception при несуществующем id")
    public void testValidByIdNull(){
        Mockito.when(userRepository.findById(anyInt())).thenThrow(new RuntimeException("1"));
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.validById(1));
        assertEquals("1",exception.getMessage());
        Mockito.verify(userRepository, Mockito.times(1)).findById(anyInt());
    }
}
