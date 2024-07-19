package etu.spb.etu.Internet.news.newspaper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import etu.spb.etu.Internet.news.newspaper.common.exception.ErrorHandler;
import etu.spb.etu.Internet.news.newspaper.common.exception.IdNotFoundException;
import etu.spb.etu.Internet.news.newspaper.user.controller.UserController;
import etu.spb.etu.Internet.news.newspaper.user.dto.UserDto;
import etu.spb.etu.Internet.news.newspaper.user.mapper.UserMapper;
import etu.spb.etu.Internet.news.newspaper.user.model.User;
import etu.spb.etu.Internet.news.newspaper.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@ContextConfiguration(classes = {UserController.class, ErrorHandler.class})
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserService userService;


    private String URL;

    @BeforeEach
    void setUp() {
        URL = "http://localhost:8080/users";
    }

    @Test
    @WithMockUser
    void getByIdNotFound() throws Exception {
        UserDto user = UserDto.builder()
                .id(1L)
                .name("test")
                .surname("test")
                .password("12345")
                .email("test@test.com")
                .build();

        when(userService.getUserById(anyLong()))
                .thenThrow(IdNotFoundException.class);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get(URL.concat("/{id}"), 1L));

        response.andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser
    void getById() throws Exception {

        User user = User.builder()
                .id(1L)
                .name("test")
                .surname("test")
                .password("12345")
                .email("test@test.com")
                .build();

        UserDto result = UserMapper.userToUserDto(user);

        when(userService.getUserById(1L))
                .thenReturn(result);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", 1L));

        response.andExpect(status().isOk());
    }

}
