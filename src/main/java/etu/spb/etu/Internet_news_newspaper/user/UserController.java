package etu.spb.etu.Internet_news_newspaper.user;

import etu.spb.etu.Internet_news_newspaper.user.model.User;
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
    public List<User> getAll() {
        return userService.getAllUsers();
    }


    @PostMapping
    public User create(@RequestBody User user) {
        return null;
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {

    }


}
