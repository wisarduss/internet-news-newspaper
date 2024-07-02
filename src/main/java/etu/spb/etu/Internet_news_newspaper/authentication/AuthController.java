package etu.spb.etu.Internet_news_newspaper.authentication;

import etu.spb.etu.Internet_news_newspaper.authentication.service.AuthenticationService;
import etu.spb.etu.Internet_news_newspaper.user.dto.UserDto;
import etu.spb.etu.Internet_news_newspaper.user.mapper.UserMapper;
import etu.spb.etu.Internet_news_newspaper.user.model.User;
import etu.spb.etu.Internet_news_newspaper.util.JWTUtil;
import etu.spb.etu.Internet_news_newspaper.util.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

    private final UserValidator userValidator;
    private final AuthenticationService authenticationService;
    private final JWTUtil jwtUtil;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") UserDto userCreateDto) {
        return "registration";
    }

    @PostMapping("/registration")
    public Map<String, String> performRegistration(@RequestBody @Valid UserDto userCreateDto,
                                                   BindingResult bindingResult) {

        userValidator.validate(userCreateDto, bindingResult);

        User user = UserMapper.userDtoToUser(userCreateDto);

        if (bindingResult.hasErrors()) {
           return Collections.singletonMap("message", "Ошибка!");
        }

        authenticationService.register(user);

        String token = jwtUtil.generateToken(user.getEmail());
        return Collections.singletonMap("jwt-token", token);
    }

}
