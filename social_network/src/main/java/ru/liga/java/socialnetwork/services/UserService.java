package ru.liga.java.socialnetwork.services;

import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.liga.java.socialnetwork.domains.User;
import ru.liga.java.socialnetwork.dto.UserDTOForRegistration;
import ru.liga.java.socialnetwork.enums.Gender;
import ru.liga.java.socialnetwork.repositories.UserRepository;
import ru.liga.java.socialnetwork.specifications.UserSpecification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.liga.java.socialnetwork.specifications.UserSpecification.*;

@Getter
@Setter
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

//    public Integer add(String email, String firstName, String lastName) throws Exception{
//        if(userRepository.findByEmail(email).isPresent()){
//            throw new Exception("Пользователь с указанным адресом почты уже существует.");
//        }
//        User userNew = userRepository.save(new User(email, firstName, lastName));
//        return userNew.getId();
//    }

    public List<User> getAllUsers(){
        List<User> userList = new ArrayList<>();
        Iterable<User> users = userRepository.findAll();
        users.forEach(userList::add);
        if(userList.isEmpty()){
            return null;
        }
        return userList;
    }

    public Integer update(User user, Integer id) throws Exception {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            User userInDB = optionalUser.get();
            if(user.getEmail() != null){
                userInDB.setEmail(user.getEmail());
            }
            if(user.getFirstName() != null){
                userInDB.setFirstName(user.getFirstName());
            }
            if(user.getLastName() != null){
                userInDB.setLastName(user.getLastName());
            }
            if(user.getAge() != null){
                userInDB.setAge(user.getAge());
            }
            if(user.getGender() != null){
                userInDB.setGender(user.getGender());
            }
            if(user.getInterests() != null){
                userInDB.setInterests(user.getInterests());
            }
            if(user.getTown() != null){
                userInDB.setTown(user.getTown());
            }
            userRepository.save(userInDB);
            return userInDB.getId();
        }
//        add(user.getEmail(), user.getFirstName(), user.getLastName());
        add(convertToDto(optionalUser.get()));
        return update(user, user.getId());
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    public User getUser(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    public List<User> getUsersWithParams(String firstName, String lastName, Integer age, Gender gender, String town){
        return userRepository.findAll(Specification.where(getUserByFirstNameSpec(firstName)
                .and(getUserByLastNameSpec(lastName).and(getUserByAgeSpec(age).and(getUserByGenderSpec(gender).and(getUserByTownSpec(town)))))));
    }

    public List<User> getUsersWithParams(String firstName, String lastName){
        return userRepository.findAll(Specification.where(getUserByFirstNameSpec(firstName)
                .and(getUserByLastNameSpec(lastName))));
    }

    public Integer add(UserDTOForRegistration userDTO) throws Exception{
        User user = convertToEntity(userDTO);
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new Exception("Пользователь с указанным адресом почты уже существует.");
        }
        User userNew = userRepository.save(user);
        return userNew.getId();
    }

    private UserDTOForRegistration convertToDto(User user) {
        return modelMapper.map(user, UserDTOForRegistration.class);
    }

    private User convertToEntity(UserDTOForRegistration userDTOForRegistration) {
        return modelMapper.map(userDTOForRegistration, User.class);
    }
}
