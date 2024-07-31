package etu.spb.etu.Internet.news.newspaper.user.service;

import etu.spb.etu.Internet.news.newspaper.user.dto.UserDto;
import etu.spb.etu.Internet.news.newspaper.user.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();

    UserDto getUserById(Long id);

    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;

    UserDetails create(UserDto userDto, String email);

    User getAuthenticatedUser();


}
