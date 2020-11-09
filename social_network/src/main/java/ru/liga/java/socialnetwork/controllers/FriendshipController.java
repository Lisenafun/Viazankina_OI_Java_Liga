package ru.liga.java.socialnetwork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.java.socialnetwork.domains.User;
import ru.liga.java.socialnetwork.services.FriendshipService;

import java.util.List;

@RestController
@RequestMapping("friends")
public class FriendshipController {

    @Autowired
    private FriendshipService friendshipService;

    @GetMapping("{userId}")
    @ResponseBody
    public ResponseEntity<List<User>> getFriends(@PathVariable Integer userId){
        List<User> friends = friendshipService.getFriendList(userId);
        friends.forEach(System.out::println);
        return new ResponseEntity<>(friends, HttpStatus.OK);
    }

    @PutMapping("{userId}/{friendId}")
    public ResponseEntity<Integer> addFriend(@PathVariable Integer userId, @PathVariable Integer friendId){
        boolean success = friendshipService.addFriend(userId, friendId);
        if(success){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{userId}/{friendId}")
    public ResponseEntity<User> deleteFriend(@PathVariable Integer userId, @PathVariable Integer friendId){
        boolean success = friendshipService.deleteFriend(userId,friendId);
        if(success){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
