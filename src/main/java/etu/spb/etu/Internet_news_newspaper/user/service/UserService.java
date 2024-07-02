package etu.spb.etu.Internet_news_newspaper.user.service;

import etu.spb.etu.Internet_news_newspaper.user.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();

    UserDto getUserById(Long id);

    void deleteUserById(Long id);
}
