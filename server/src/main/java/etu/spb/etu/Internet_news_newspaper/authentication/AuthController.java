package etu.spb.etu.Internet_news_newspaper.authentication;

import etu.spb.etu.Internet_news_newspaper.authentication.dto.AuthenticationDto;
import etu.spb.etu.Internet_news_newspaper.authentication.service.AuthenticationService;
import etu.spb.etu.Internet_news_newspaper.user.dto.UserDto;
import etu.spb.etu.Internet_news_newspaper.user.mapper.UserMapper;
import etu.spb.etu.Internet_news_newspaper.user.model.User;
import etu.spb.etu.Internet_news_newspaper.util.JWTUtil;
import etu.spb.etu.Internet_news_newspaper.util.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;


@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserValidator userValidator;
    private final AuthenticationService authenticationService;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

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
        log.debug("Получен POST запрос на регистрацию пользователя {}", userCreateDto);
        return Collections.singletonMap("jwt-token", token);
    }

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody AuthenticationDto authenticationDto) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationDto.getEmail(),authenticationDto.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return Collections.singletonMap("message", "Incorrect credentials");
        }

        String token = jwtUtil.generateToken(authenticationDto.getEmail());
        log.debug("Получен POST запрос на аутентификацию пользователя");
        return Collections.singletonMap("jwt-token", token);
    }


}
