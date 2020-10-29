package ru.liga.java.socialnetwork.domains;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "FRIENDSHIP")
public class Friendship {

    @Embeddable
    @Data
    static class UserFriendKey implements Serializable {

        @Column(name = "user_id")
        Integer userId;

        @Column(name = "user_id")
        Integer friendId;
    }

    @EmbeddedId
    private UserFriendKey id;

    @Column(name = "USER_ID")
    private User user;

    @Column(name = "FRIEND_ID")
    private User friend;

//    private boolean isActive;
}
