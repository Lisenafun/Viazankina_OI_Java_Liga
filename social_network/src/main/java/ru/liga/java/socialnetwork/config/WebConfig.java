package ru.liga.java.socialnetwork.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.liga.java.socialnetwork.enums.StringToEnumConverter;

/**
 * Класс конфигурации
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Метод добавления конвертера в конфигурацию
     *
     * @param registry Регистрация
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToEnumConverter());
    }
}
