package ru.liga.java.socialnetwork.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.java.socialnetwork.dto.UserRegistrationDto;
import ru.liga.java.socialnetwork.dto.UserEditDto;
import ru.liga.java.socialnetwork.services.UserService;
import ru.liga.java.socialnetwork.services.filters.UserFilter;

import java.util.List;

/**
 * Контроллер для работы с сущностью User
 */
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    /**
     * Поле Сервис пользователя
     */
    private final UserService userService;

    /**
     * Контроллер для добавления пользователя
     *
     * @param userDTO Dto для регистрации пользователя
     * @return Статус 200 и id пользователя при успехе, иначе RuntimeException и статус 404
     */
    @PostMapping
    public ResponseEntity<String> add(UserRegistrationDto userDTO){
        Integer id;
        try {
            id = userService.add(userDTO);
        } catch(RuntimeException e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(id.toString(), HttpStatus.OK);
    }


    /**
     * Контроллер для получения пользователя по id
     *
     * @param id Идентификатор пользователя
     * @return Статус 200 и Dto для отображения данных при успехе, иначе пустое тело ответа и статус 404
     */
    @GetMapping("{id}")
    @ResponseBody
    public ResponseEntity<UserEditDto> get(@PathVariable Integer id){
        UserEditDto userDto = userService.findOne(id);
        if(userDto == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    /**
     * Контроллер для получения списка пользователей по фильтру
     *
     * @param filter Фильтр для фильтрации списка пользователей
     * @return Статус 200 и список пользователей в формате Dto при успехе, иначе пустое тело ответа и статус 404
     */
    @GetMapping
    @ResponseBody
    public ResponseEntity <List<UserEditDto>> getUsersWithFilters(UserFilter filter){
        List<UserEditDto> users =  userService.findWithFilter(filter);
        if(users.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Контроллер для изменения данных пользователя
     *
     * @param userDTO Dto c данными пользователя для изменения
     * @param id Идентификатор пользователя, поля которого меняем
     * @return id и статус 200 при успехе, иначе RuntimeException и статус 400
     */
    @PutMapping("{id}")
    public ResponseEntity<String> update(UserEditDto userDTO, @PathVariable Integer id){
        Integer updateUserId;
        try{
            updateUserId = userService.update(userDTO, id);
        } catch(RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(updateUserId.toString(), HttpStatus.OK);
    }

    /**
     * Контроллер для удаления пользователя
     *
     * @param id Идентификатор пользователя
     * @return пустое тело ответа и статус 204 при успехе, иначе RuntimeException и статус 404
     */
    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id){
        try{
            userService.delete(id);
        }catch(RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
