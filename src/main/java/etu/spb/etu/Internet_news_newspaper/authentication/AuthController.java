package etu.spb.etu.Internet_news_newspaper.authentication;

import etu.spb.etu.Internet_news_newspaper.authentication.service.AuthenticationService;
import etu.spb.etu.Internet_news_newspaper.user.dto.UserDto;
import etu.spb.etu.Internet_news_newspaper.util.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

    private final UserValidator userValidator;
    private final AuthenticationService authenticationService;
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") UserDto userCreateDto) {
        return "registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("user") @Valid UserDto userCreateDto,
                                      BindingResult bindingResult) {

        userValidator.validate(userCreateDto, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/registration";
        }

        authenticationService.register(userCreateDto);

         return "redirect:/login";
    }
}
