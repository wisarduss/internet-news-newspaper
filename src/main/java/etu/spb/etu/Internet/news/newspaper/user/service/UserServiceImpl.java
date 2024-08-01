package etu.spb.etu.Internet.news.newspaper.user.service;

import etu.spb.etu.Internet.news.newspaper.authentication.security.PersonDetails;
import etu.spb.etu.Internet.news.newspaper.common.exception.AlreadyExistException;
import etu.spb.etu.Internet.news.newspaper.common.exception.IdNotFoundException;
import etu.spb.etu.Internet.news.newspaper.common.exception.NotOwnerException;
import etu.spb.etu.Internet.news.newspaper.user.dto.UserDto;
import etu.spb.etu.Internet.news.newspaper.user.mapper.UserMapper;
import etu.spb.etu.Internet.news.newspaper.user.model.User;
import etu.spb.etu.Internet.news.newspaper.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public List<UserDto> getAllUsers() {
        log.debug("Получены все пользователи");
        return userRepository.findAll().stream()
                .map(UserMapper::userToUserDto)
                .collect(Collectors.toList());
    }


    @Override
    public UserDto getUserById(Long id) {
        log.debug("Получен пользователь во id = {} ", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(String.format("Пользователь с id = %d не найден", id)));
        return UserMapper.userToUserDto(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(s);

        if (!user.isPresent()) {
            throw new UsernameNotFoundException(String.format("Пользователь с email = %s не найден", s));
        }

        return new PersonDetails(user.get());
    }

    @Override
    @Transactional
    public UserDetails create(UserDto userDto, String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            throw new AlreadyExistException("Пользователь с таким Email уже существует");
        }

        User saveUser = UserMapper.userDtoToUser(userDto);

        return new PersonDetails(saveUser);
    }

    @Override
    public User getAuthenticatedUser() {
        PersonDetails principal = (PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByEmail(principal.getUsername());
        if (!user.isPresent()) {
            throw new NotOwnerException("не пользователь");
        }

        return user.get();
    }

}
