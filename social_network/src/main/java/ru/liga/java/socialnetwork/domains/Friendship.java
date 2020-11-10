package ru.liga.java.socialnetwork.domains;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


/**
 * Сущность Дружба
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "Friendship")
public class Friendship implements Serializable {

    /**
     * Поле Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "frienship_id", columnDefinition = "SERIAL")
    private Integer id;

    /**
     * Поле Пользователя, который дружит
     */
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User owner;

    /**
     * Поле Пользователя, с которым дружат
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id", nullable = false, updatable = false)
    private User friend;

    public Friendship(User owner, User friend){
        this.owner = owner;
        this.friend = friend;
    }
}
