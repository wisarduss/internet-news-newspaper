package etu.spb.etu.Internet.news.newspaper.service;

import etu.spb.etu.Internet.news.newspaper.authentication.AuthController;
import etu.spb.etu.Internet.news.newspaper.authentication.config.JWTFilter;
import etu.spb.etu.Internet.news.newspaper.authentication.security.PersonDetails;
import etu.spb.etu.Internet.news.newspaper.authentication.service.AuthenticationService;
import etu.spb.etu.Internet.news.newspaper.common.util.JWTUtil;
import etu.spb.etu.Internet.news.newspaper.like.dto.LikeDto;
import etu.spb.etu.Internet.news.newspaper.like.mapper.LikeMapper;
import etu.spb.etu.Internet.news.newspaper.like.model.Like;
import etu.spb.etu.Internet.news.newspaper.like.repository.LikeRepository;
import etu.spb.etu.Internet.news.newspaper.like.service.LikeService;
import etu.spb.etu.Internet.news.newspaper.post.model.Post;
import etu.spb.etu.Internet.news.newspaper.post.repository.PostRepository;
import etu.spb.etu.Internet.news.newspaper.user.model.User;
import etu.spb.etu.Internet.news.newspaper.user.repository.UserRepository;
import etu.spb.etu.Internet.news.newspaper.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private UserService userService;

    private User authenticatedUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        authenticatedUser = new User();
        authenticatedUser.setId(1L);
        authenticatedUser.setEmail("test@example.com");

        PersonDetails personDetails = new PersonDetails(authenticatedUser);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(personDetails);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);
    }

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

        LikeDto likeDto = LikeDto.builder()
                .id(1L)
                .postId(user.getId())
                .userId(post.getId())
                .build();

        when(likeRepository.save(any()))
                .thenReturn(LikeMapper.likeDtoToLike(likeDto,user, post));

        LikeDto result = likeService.create(likeDto);

        assertThat(result).usingRecursiveComparison().isEqualTo(likeDto);
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void deleteLike() {
        Post post = Post.builder()
                .id(1L)
                .title("title")
                .description("description")
                .photoURL("test")
                .userId(authenticatedUser.getId())
                .build();

        Like like = Like.builder()
                .id(1L)
                .post(post)
                .user(authenticatedUser)
                .build();

        when(userRepository.findByEmail(authenticatedUser.getEmail()))
                .thenReturn(Optional.of(authenticatedUser));
        when(likeRepository.findById(1L)).thenReturn(Optional.of(like));

        likeService.deleteLike(1L);

        verify(likeRepository, times(1)).deleteById(1L);
    }

}
