package ru.liga.java.socialnetwork.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.java.socialnetwork.domains.User;
import ru.liga.java.socialnetwork.dto.UserDTOForRegistration;
import ru.liga.java.socialnetwork.enums.Gender;
import ru.liga.java.socialnetwork.services.UserService;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

//    @PostMapping
//    public ResponseEntity<String> add(@RequestParam(name = "email") String email,
//                                      @RequestParam(name = "firstName") String firstName,
//                                      @RequestParam(name = "lastName") String lastName){
//        Integer id;
//        try {
//            id = userService.add(email, firstName, lastName);
//        } catch(Exception e) {
//            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>(id.toString(), HttpStatus.OK);
//    }

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
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @GetMapping("all")
    @ResponseBody
    public ResponseEntity<List<User>> getAll(){
        List<User> users = userService.getAllUsers();
        if(users.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<User>> getUsersWithParams(@RequestParam(name = "firstName", required = false) String firstName,
                                                         @RequestParam(name = "lastName", required = false) String lastName
//                                                         ,@RequestParam(name = "age", required = false) String age,
//                                                         @RequestParam(name = "gender", required = false, defaultValue = "UNDEFINED") Gender gender,
//                                                         @RequestParam(name = "town", required = false) String town
                                                         ){

//        List<User> users = userService.getUsersWithParams(firstName, lastName, Integer.getInteger(age), gender, town);
        List<User> users = userService.getUsersWithParams(firstName, lastName);
        if(users.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> update(User user, @PathVariable Integer id){
        Integer updateUserId;
        try {
            updateUserId = userService.update(user, id);
        } catch(Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(updateUserId.toString(), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<User> delete(@PathVariable Integer id){
        userService.delete(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
