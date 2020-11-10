package ru.liga.java.socialnetwork.domains;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import ru.liga.java.socialnetwork.enums.Gender;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


/**
 * Сущность Пользователь
 */
@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "friends")
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class User implements Serializable {

    /**
     * Поле Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "SERIAL")
    private Integer id;

    /**
     * Поле Адрес почты (уникальное, не может быть пустым)
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Поле Имя (не может быть пустым)
     */
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /**
     * Поле Фамилия (не может быть пустым)
     */
    @Column(name = "last_name", nullable = false)
    private String lastName;

    /**
     * Поле Возраст
     */
    @Column(name = "age")
    private Integer age;

    /**
     * Поле Пол (по умолчанию - не определен)
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender = Gender.UNDEFINED;

    /**
     * Поле Интересы
     */
    @Column(name = "interests")
    private String interests;

    /**
     * Поле город
     */
    @Column(name = "town")
    private String town;

    /**
     * Поле Список друзейб связанный с сущностью Friendship
     */
    @Transient
    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(name = "friendship",
            joinColumns = {@JoinColumn(name = "user_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "friend_id", nullable = false, updatable = false)})
    private Set<User> friends = new HashSet<>();

    public User(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", email='" + email + '\'' + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", age=" + age + ", gender=" + gender + ", interests='" + interests + '\'' + ", town='" + town + '}';
    }
}
