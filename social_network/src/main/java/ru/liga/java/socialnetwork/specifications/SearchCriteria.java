package ru.liga.java.socialnetwork.specifications;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchCriteria {

    private String key;
    private String operation;
    private Object value;
}