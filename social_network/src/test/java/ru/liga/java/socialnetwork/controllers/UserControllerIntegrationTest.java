package ru.liga.java.socialnetwork.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.liga.java.socialnetwork.dto.UserEditDto;
import ru.liga.java.socialnetwork.dto.UserRegistrationDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;
    
//    @Autowired
//    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

//    @BeforeEach
//    public void setup() {
//
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .build();
//
//    }

    @Test
    @DisplayName("Проверка на возврат статуса 200 и верного id")
    public void testAddUserStatusOkAndContentOk() throws Exception {
//        UserEditDto UserEditDto = new UserEditDto("fedorov@mail.ru", "Fedor", "Fedorov","Dancing", 15, "Male", "Moscow");
        UserRegistrationDto userDto = new UserRegistrationDto("fedorov@mail.ru", "Fedor", "Fedorov");
        mockMvc.perform(post("/api/v1/users/")
//                .content("{email=[pet@mail.ru], firstName=[Ivan], lastName=[Ivanov]}")
                .content(objectMapper.writeValueAsString(userDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
