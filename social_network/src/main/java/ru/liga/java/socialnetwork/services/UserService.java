package ru.liga.java.socialnetwork.services;

import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.liga.java.socialnetwork.domains.User;
import ru.liga.java.socialnetwork.dto.UserRegistrationDto;
import ru.liga.java.socialnetwork.dto.UserEditDto;
import ru.liga.java.socialnetwork.enums.Gender;
import ru.liga.java.socialnetwork.repositories.FriendshipRepository;
import ru.liga.java.socialnetwork.repositories.UserRepository;
import ru.liga.java.socialnetwork.services.filters.UserFilter;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Getter
@Setter
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;
    private final ModelMapper modelMapper;

    public UserEditDto findOne(Integer id) {
        return userRepository.findById(id)
                .map(this::convertToEditDto)
                .orElse(null);
    }

    public List<UserEditDto> findWithFilter(UserFilter filter) {
        return userRepository.findAll(filter.toSpecification())
                .stream().map(this::convertToEditDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public Integer add(UserRegistrationDto userDTO) throws RuntimeException {
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
        throw new RuntimeException("Введите корректный e-mail.");
    }

    @Transactional
    public Integer update(UserEditDto userDTO, Integer id) throws RuntimeException{
        User entity = validById(id);
        if(StringUtils.isEmpty(userDTO.getEmail())){
            userDTO.setEmail(entity.getEmail());
        }
        if(StringUtils.isEmpty(userDTO.getFirstName())){
            userDTO.setFirstName(entity.getFirstName());
        }
        if(StringUtils.isEmpty(userDTO.getLastName())){
            userDTO.setLastName(entity.getLastName());
        }
        if(validEmail(userDTO.getEmail())){
            User user = convertFromEditDto(userDTO, entity);
            user = userRepository.save(user);
            return user.getId();
        }
        throw new RuntimeException("Введите корректный e-mail.");
    }

    public void delete(Integer id) {
        User entity = validById(id);
        friendshipRepository.findByOwner(entity).forEach(friend -> {
            friendshipRepository.deleteByOwnerAndFriend(entity, friend);
            friendshipRepository.deleteByOwnerAndFriend(friend, entity);
        });
        userRepository.deleteById(id);
    }

    protected User validById(Integer id) {
        return Optional.ofNullable(id)
                .flatMap(userRepository::findById)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден!"));
    }

    private boolean validEmail(String email) {
        Pattern pattern = Pattern.compile("\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,4}");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    protected User convertFromDtoReg(UserRegistrationDto userRegistrationDto) {
        return modelMapper.map(userRegistrationDto, User.class);
    }

    protected User convertFromEditDto(UserEditDto dtoForUser, User user) {
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

    protected UserEditDto convertToEditDto(User user) {
        return modelMapper.map(user, UserEditDto.class);
//        UserEditDto dtoForUser = new UserEditDto();
//        dtoForUser.setEmail(user.getEmail());
//        dtoForUser.setFirstName(user.getFirstName());
//        dtoForUser.setLastName(user.getLastName());
//        dtoForUser.setAge(user.getAge());
//        dtoForUser.setTown(user.getTown());
//        dtoForUser.setInterests(user.getInterests());
//        dtoForUser.setGender(user.getGender().toString());
//        return dtoForUser;
    }
}
