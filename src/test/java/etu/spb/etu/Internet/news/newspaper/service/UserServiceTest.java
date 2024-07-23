package etu.spb.etu.Internet.news.newspaper.service;

import etu.spb.etu.Internet.news.newspaper.authentication.AuthController;
import etu.spb.etu.Internet.news.newspaper.authentication.config.JWTFilter;
import etu.spb.etu.Internet.news.newspaper.authentication.service.AuthenticationService;
import etu.spb.etu.Internet.news.newspaper.common.exception.IdNotFoundException;
import etu.spb.etu.Internet.news.newspaper.common.util.JWTUtil;
import etu.spb.etu.Internet.news.newspaper.user.dto.UserDto;
import etu.spb.etu.Internet.news.newspaper.user.mapper.UserMapper;
import etu.spb.etu.Internet.news.newspaper.user.repository.UserRepository;
import etu.spb.etu.Internet.news.newspaper.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@Profile("test")
public class UserServiceTest {

    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private JWTFilter jwtFilter;

    @MockBean
    private JWTUtil jwtUtil;

    @MockBean
    private AuthController authController;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void findAll() {
        UserDto user = UserDto.builder()
                .id(1L)
                .name("tests")
                .surname("test")
                .password("12345")
                .email("test@test.com")
                .build();

        when(userRepository.findAll())
                .thenReturn(Collections.singletonList(UserMapper.userDtoToUser(user)));

        List<UserDto> result = userService.getAllUsers();
        assertThat(result).usingRecursiveComparison().isEqualTo(Collections.singletonList(user));
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getById() {
        UserDto user = UserDto.builder()
                .id(1L)
                .name("test")
                .surname("test")
                .password("12345")
                .email("test@test.com")
                .build();

        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(UserMapper.userDtoToUser(user)));

        UserDto result = userService.getUserById(1L);
        assertThat(result).usingRecursiveComparison().isEqualTo(user);
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    void getByIdNotFoundException() {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> userService.getUserById(1L));
        verify(userRepository, times(1)).findById(anyLong());
    }

}
