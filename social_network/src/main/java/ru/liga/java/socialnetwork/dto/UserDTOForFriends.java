package ru.liga.java.socialnetwork.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTOForFriends extends UserDTOForAll{

    private String email;

    private String interests;

    private Integer age;

}
