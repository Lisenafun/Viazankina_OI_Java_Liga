package ru.liga.java.socialnetwork.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.java.socialnetwork.dto.UserEditDto;
import ru.liga.java.socialnetwork.services.FriendshipService;

import java.util.List;

/**
 * Контроллер отношений с друзьями
 */
@RestController
@RequestMapping("friends")
@RequiredArgsConstructor
public class FriendshipController {

    /**
     * Сервис сущности Friendship
     */
    private final FriendshipService friendshipService;

    /**
     * Контроллер для получения списка друзей
     *
     * @param userId Идентификатор пользователя
     * @return Статус 200 и список друзей при успехе, иначе null и статус 404
     */
    @GetMapping("{userId}")
    @ResponseBody
    public ResponseEntity<List<UserEditDto>> getFriends(@PathVariable Integer userId){
        List<UserEditDto> friends;
        try {
            friends = friendshipService.getFriendList(userId);
        }catch(RuntimeException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(friends, HttpStatus.OK);
    }

    /**
     * Контроллер для добавления друга
     *
     * @param userId Идентификатор пользователя
     * @param friendId Идентификатор друга
     * @return Статус 200 и id дружбы при успехе, иначе RuntimeException и статус 400
     */
    @PostMapping
    public ResponseEntity<String> addFriend(Integer userId, Integer friendId) {
        Integer id;
        try {
            id = friendshipService.addFriend(userId, friendId);
        } catch(RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(id.toString(), HttpStatus.OK);
    }

    /**
     * Контроллер для удаления друга
     *
     * @param userId Идентификатор пользователя
     * @param friendId Идентификатор друга
     * @return Статус 204 и пустое тело ответа, иначе RuntimeException и статус 404
     */
    @DeleteMapping("{userId}/{friendId}")
    public ResponseEntity<String> deleteFriend(@PathVariable Integer userId, @PathVariable Integer friendId){
        try {
            friendshipService.deleteFriend(userId, friendId);
        }catch(RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
