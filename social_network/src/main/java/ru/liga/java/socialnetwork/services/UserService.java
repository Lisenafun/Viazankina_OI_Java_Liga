package ru.liga.java.socialnetwork.services;

import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.liga.java.socialnetwork.domains.User;
import ru.liga.java.socialnetwork.dto.UserDTOForRegistration;
import ru.liga.java.socialnetwork.dto.UserDTOForUser;
import ru.liga.java.socialnetwork.enums.Gender;
import ru.liga.java.socialnetwork.repositories.UserRepository;
import ru.liga.java.socialnetwork.services.filters.UserFilter;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        Iterable<User> users = userRepository.findAll();
        users.forEach(userList::add);
        if(userList.isEmpty()) {
            return null;
        }
        return userList;
    }

    @Transactional
    public Integer add(UserDTOForRegistration userDTO) throws RuntimeException {
        if(StringUtils.isEmpty(userDTO.getFirstName()) || StringUtils.isEmpty(userDTO.getLastName())) {
            throw new RuntimeException("Поля не могут быть пустыми.");
        }
        if(validEmail(userDTO.getEmail())) {
            User user = convertFromDtoReg(userDTO);
            if(userRepository.findByEmail(user.getEmail()).isPresent()) {
                throw new RuntimeException("Пользователь с указанным адресом почты уже существует.");
            }
            user = userRepository.save(user);
            return user.getId();
        }
        ;
        throw new RuntimeException("Введите корректный e-mail.");
    }

    @Transactional
    public Integer update(UserDTOForUser userDTO, Integer id) {
        User entity = Optional.ofNullable(id)
                .flatMap(userRepository::findById)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден!"));

        User user = convertFromDtoForUser(userDTO, entity);
        user = userRepository.save(user);
        return user.getId();
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    public User findOne(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    public List<User> findWithFilter(UserFilter filter) {
        return userRepository.findAll(filter.toSpecification());
    }

    private boolean validEmail(String email) {
        Pattern pattern = Pattern.compile("\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,4}");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private UserDTOForRegistration convertToDtoReg(User user) {
        return modelMapper.map(user, UserDTOForRegistration.class);
    }

    private User convertFromDtoReg(UserDTOForRegistration userDTOForRegistration) {
        return modelMapper.map(userDTOForRegistration, User.class);
    }

    private User convertFromDtoForUser(UserDTOForUser dtoForUser, User user) {
        user.setFirstName(dtoForUser.getFirstName());
        user.setLastName(dtoForUser.getLastName());
        user.setAge(dtoForUser.getAge());
        user.setTown(dtoForUser.getTown());
        user.setInterests(dtoForUser.getInterests());
        user.setEmail(dtoForUser.getEmail());
        if(StringUtils.isEmpty(dtoForUser.getGender())) {
            user.setGender(Gender.UNDEFINED);
        } else if(dtoForUser.getGender().equalsIgnoreCase(Gender.MALE.toString())) {
            user.setGender(Gender.MALE);
        } else if(dtoForUser.getGender().equalsIgnoreCase(Gender.FEMALE.toString())) {
            user.setGender(Gender.FEMALE);
        }
        return user;
    }
}
