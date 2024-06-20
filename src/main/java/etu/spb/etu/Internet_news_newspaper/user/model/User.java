package etu.spb.etu.Internet_news_newspaper.user.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    @NotBlank
    private String name;
    @Column(name = "surname")
    @NotBlank
    private String surname;
    @Column(name = "email")
    @NotBlank
    @Email
    private String email;
    @Column(name = "password")
    @NotBlank
    private String password;
}
