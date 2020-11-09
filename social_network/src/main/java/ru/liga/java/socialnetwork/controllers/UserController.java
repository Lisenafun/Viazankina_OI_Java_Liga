package ru.liga.java.socialnetwork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.java.socialnetwork.domains.User;
import ru.liga.java.socialnetwork.dto.UserDTOForRegistration;
import ru.liga.java.socialnetwork.dto.UserDTOForUser;
import ru.liga.java.socialnetwork.services.UserService;
import ru.liga.java.socialnetwork.services.filters.UserFilter;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<String> add(UserDTOForRegistration userDTO){
        Integer id;
        try {
            id = userService.add(userDTO);
        } catch(Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(id.toString(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @ResponseBody
    public ResponseEntity<User> get(@PathVariable Integer id){
        User user = userService.findOne(id);
        if(user == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("all")
    @ResponseBody
    public ResponseEntity<List<User>> getAll(){
        List<User> users = userService.findAll();
        if(users.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity <List<User>> getUsersWithFilters(UserFilter filter){
        List<User> users =  userService.findWithFilter(filter);
        if(users.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> update(UserDTOForUser userDTO, @PathVariable Integer id){
        Integer updateUserId = userService.update(userDTO, id);
        return new ResponseEntity<>(updateUserId.toString(), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<User> delete(@PathVariable Integer id){
        userService.delete(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
