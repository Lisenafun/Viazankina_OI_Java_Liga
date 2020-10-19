package service_order.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Проверка на возврат статуса 200 и верного id")
    public void testCreateOrderStatusOkAndContentOk() throws Exception {
        mockMvc.perform(post("/orders/")
                .param("name", "book")
                .param("price", "1500")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    @DisplayName("Проверка на возврат статуса 400 и сообщения об ошибке при неверно заполненном поле price")
    public void testCreateOrderStatusBadRequestWithBadPrice() throws Exception {
        mockMvc.perform(post("/orders/")
                .param("name", "book")
                .param("price", "-15")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Некорректно заполненный заказ."));
    }

    @Test
    @DisplayName("Проверка на возврат статуса 400 и сообщения об ошибке при пустом поле name")
    public void testCreateOrderStatusBadRequestWithEmptyName() throws Exception {
        mockMvc.perform(post("/orders/")
                .param("name", "")
                .param("price", "1500")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Некорректно заполненный заказ."));
    }
}
