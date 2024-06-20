package etu.spb.etu.Internet_news_newspaper.authentication.service;

import etu.spb.etu.Internet_news_newspaper.user.dto.UserCreateDto;
import etu.spb.etu.Internet_news_newspaper.user.model.User;

public interface AuthenticationService {
    void register(UserCreateDto userCreateDto);
}
