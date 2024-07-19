package etu.spb.etu.Internet.news.newspaper.user.controller;

import etu.spb.etu.Internet.news.newspaper.user.dto.UserDto;
import etu.spb.etu.Internet.news.newspaper.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> getAll() {
        log.debug("Получен GET запрос на получение всех пользователей");
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id) {
        log.debug("Получен GET запрос на получение пользователя по id = {}", id);
        return userService.getUserById(id);
    }

}
