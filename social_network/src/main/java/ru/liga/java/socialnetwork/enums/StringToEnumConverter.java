package ru.liga.java.socialnetwork.enums;

import org.springframework.core.convert.converter.Converter;

public class StringToEnumConverter implements Converter<String, Gender> {

    @Override
    public Gender convert(String source) {
        return Gender.valueOf(source.toUpperCase());
    }
}
