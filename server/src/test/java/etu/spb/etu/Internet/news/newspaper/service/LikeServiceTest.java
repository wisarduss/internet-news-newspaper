package etu.spb.etu.Internet.news.newspaper.service;

import etu.spb.etu.Internet.news.newspaper.authentication.AuthController;
import etu.spb.etu.Internet.news.newspaper.authentication.config.JWTFilter;
import etu.spb.etu.Internet.news.newspaper.authentication.service.AuthenticationService;
import etu.spb.etu.Internet.news.newspaper.common.util.JWTUtil;
import etu.spb.etu.Internet.news.newspaper.like.model.Like;
import etu.spb.etu.Internet.news.newspaper.like.repository.LikeRepository;
import etu.spb.etu.Internet.news.newspaper.like.service.LikeService;
import etu.spb.etu.Internet.news.newspaper.post.model.Post;
import etu.spb.etu.Internet.news.newspaper.post.repository.PostRepository;
import etu.spb.etu.Internet.news.newspaper.user.repository.UserRepository;
import etu.spb.etu.Internet.news.newspaper.user.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.test.context.support.WithMockUser;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@Profile("test")
public class LikeServiceTest {

    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private JWTFilter jwtFilter;

    @MockBean
    private JWTUtil jwtUtil;

    @MockBean
    private AuthController authController;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private LikeRepository likeRepository;

    @Autowired
    private LikeService likeService;

    @Test
    @WithMockUser
    void addLike() {
        User user = User.builder()
                .id(1L)
                .name("test")
                .surname("test")
                .password("12345")
                .email("test@test.com")
                .build();

        when(userRepository.save(any()))
                .thenReturn(user);
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(user));

        Post post = Post.builder()
                .id(1L)
                .title("title")
                .description("description")
                .photoURL("test")
                .userId(user.getId())
                .build();
        when(postRepository.save(any()))
                .thenReturn(post);
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));

        Like like = Like.builder()
                .id(1L)
                .post(post)
                .user(user)
                .build();

        when(likeRepository.save(any()))
                .thenReturn(like);

        Like result = likeRepository.save(like);

        assertThat(result).usingRecursiveComparison().isEqualTo(like);
    }

    @Test
    @WithMockUser
    void deleteLike() {

        User user = User.builder()
                .id(1L)
                .name("test")
                .surname("test")
                .password("12345")
                .email("test@test.com")
                .build();

        Post post = Post.builder()
                .id(1L)
                .title("title")
                .description("description")
                .photoURL("test")
                .userId(user.getId())
                .build();

        Like like = Like.builder()
                .id(1L)
                .post(post)
                .user(user)
                .build();

        when(likeRepository.findById(anyLong()))
                .thenReturn(Optional.of(like));

        likeService.deleteLike(1L, 1L);
        verify(likeRepository, times(1)).deleteById(anyLong());
    }
}
