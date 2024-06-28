/*
package etu.spb.etu.Internet_news_newspaper.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import etu.spb.etu.Internet_news_newspaper.exception.ErrorHandler;
import etu.spb.etu.Internet_news_newspaper.user.UserController;
import etu.spb.etu.Internet_news_newspaper.user.dto.UserDto;
import etu.spb.etu.Internet_news_newspaper.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@ContextConfiguration(classes = {UserController.class, ErrorHandler.class})
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private static final String URL = "http://localhost:8080/users";

    @Test
    void getById() throws Exception {
        UserDto userDto = UserDto.builder()
                .id(1L)
                .name("test")
                .surname("test")
                .email("test@mail.ru")
                .password("12345")
                .build();


        when(userService.getUserById(anyLong()))
                .thenReturn(userDto);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get(URL.concat("/{id}"), 1L)
                        .header("Authorization", "Bearer a041405c-c4a2-48fc-956b-1d53fa26fbfd"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
*/
