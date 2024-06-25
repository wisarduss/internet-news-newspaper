package etu.spb.etu.Internet_news_newspaper.util;


import etu.spb.etu.Internet_news_newspaper.user.dto.UserDto;
import etu.spb.etu.Internet_news_newspaper.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
@Component
public class UserValidator implements Validator {

    private final UserServiceImpl userService;
    @Override
    public boolean supports(Class<?> aClass) {
        return UserDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserDto userCreateDto = (UserDto) o;
        try {
            userService.loadUserByUsername(userCreateDto.getEmail());
        } catch (UsernameNotFoundException ignored) {
            return;
        }

        errors.rejectValue("email", "", "Человек с такой почтой уже существует ");
    }

}
