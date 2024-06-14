package etu.spb.etu.Internet_news_newspaper.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class UserCreateDto {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
}
