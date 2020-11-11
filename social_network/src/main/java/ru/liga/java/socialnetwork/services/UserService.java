package ru.liga.java.socialnetwork.services;

import lombok.*;
import org.modelmapper.ModelMapper;
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
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Сервис пользователя
 */
@Getter
@Setter
@Service
@RequiredArgsConstructor
public class UserService {

    /**
     * Репозиторий пользователя
     */
    private final UserRepository userRepository;

    /**
     * Репозиторий дружбы
     */
    private final FriendshipRepository friendshipRepository;

    /**
     * Преобразователь классов
     */
    private final ModelMapper modelMapper;

    /**
     * Метод получения пользователя по id
     *
     * @param id Идентификатор пользователя
     * @return Dto пользователя
     */
    public UserEditDto findOne(Integer id) {
        return userRepository.findById(id)
                .map(this::convertToEditDto)
                .orElse(null);
    }

    /**
     * Метод поиска пользователей с помощью фильтров
     *
     * @param filter Фильтр пользователей
     * @return Список пользователей в виде Dto
     */
    public List<UserEditDto> findWithFilter(UserFilter filter) {
        return userRepository.findAll(filter.toSpecification())
                .stream().map(this::convertToEditDto)
                .collect(Collectors.toList());
    }

    /**
     * Метод добавления пользователя
     *
     * @param userDTO Dto c данными пользователя для регистрации
     * @return Идентификатор пользователя
     * @throws RuntimeException проверка на непустые поля, валидный email и дублирование email
     */
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

    /**
     * Метод изменения данных пользователя
     *
     * @param userDTO Dto данных пользователя к изменению
     * @param id Идентификатор изменяемого пользователя
     * @return Иднетификатор измененного пользователя
     * @throws RuntimeException проверка валидности email
     */
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

    /**
     * Метод удаления пользователя
     *
     * @param id Идентификатор пользователя
     */
    public void delete(Integer id) {
        User entity = validById(id);
        friendshipRepository.findByOwner(entity).forEach(friend -> {
            friendshipRepository.deleteByOwnerAndFriend(entity, friend);
            friendshipRepository.deleteByOwnerAndFriend(friend, entity);
        });
        userRepository.deleteById(id);
    }

    /**
     * Метод проверки наличия пользователя по id
     *
     * @param id Идентификатор пользователя
     * @return Пользователь
     */
    protected User validById(Integer id) {
        return Optional.ofNullable(id)
                .flatMap(userRepository::findById)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден!"));
    }

    /**
     * Метод валидации адреса почты
     *
     * @param email Адрес почты
     * @return true - если адрес почты валиден, false - если проверку не прошел
     */
    private boolean validEmail(String email) {
        Pattern pattern = Pattern.compile("\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,4}");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Метод конвертации Dto с регистрационными данными в сущность пользователь
     *
     * @param userRegistrationDto Dto с данными пользователя для регистрации
     * @return Пользователь сущность
     */
    protected User convertFromDtoReg(UserRegistrationDto userRegistrationDto) {
        return modelMapper.map(userRegistrationDto, User.class);
    }

    /**
     * Метод конвертации Dto с общими данными в сущность пользователь
     *
     * @param dtoForUser Dto c общими данными пользователя
     * @param user Пользователь сущность до изменений
     * @return Пользователь сущность после изменений
     */
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

    /**
     * Метод конвертации пользователя в Dto с общими данными
     *
     * @param user Пользователь сущность
     * @return Dto c общими данными пользователя
     */
    protected UserEditDto convertToEditDto(User user) {
        return modelMapper.map(user, UserEditDto.class);
    }
}
