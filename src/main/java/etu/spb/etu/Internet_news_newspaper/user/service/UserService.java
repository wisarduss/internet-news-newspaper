package etu.spb.etu.Internet_news_newspaper.user.service;

import etu.spb.etu.Internet_news_newspaper.user.model.User;

import java.util.List;

public interface UserService {


    List<User> getAllUsers();

    User createUser(User user);

    User getUserById(Long id);

    void deleteUserById(Long id);
}
