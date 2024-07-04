package etu.spb.etu.Internet_news_newspaper.user;

import etu.spb.etu.Internet_news_newspaper.user.dto.UserDto;
import etu.spb.etu.Internet_news_newspaper.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> getAll() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }


}
