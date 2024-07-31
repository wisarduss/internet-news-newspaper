package etu.spb.etu.Internet.news.newspaper.service;


import etu.spb.etu.Internet.news.newspaper.authentication.AuthController;
import etu.spb.etu.Internet.news.newspaper.authentication.config.JWTFilter;
import etu.spb.etu.Internet.news.newspaper.authentication.security.PersonDetails;
import etu.spb.etu.Internet.news.newspaper.authentication.service.AuthenticationService;
import etu.spb.etu.Internet.news.newspaper.comment.dto.CommentDto;
import etu.spb.etu.Internet.news.newspaper.comment.dto.CommentUpdateDto;
import etu.spb.etu.Internet.news.newspaper.comment.model.Comment;
import etu.spb.etu.Internet.news.newspaper.comment.repository.CommentRepository;
import etu.spb.etu.Internet.news.newspaper.common.exception.IdNotFoundException;
import etu.spb.etu.Internet.news.newspaper.common.util.JWTUtil;
import etu.spb.etu.Internet.news.newspaper.like.model.Like;
import etu.spb.etu.Internet.news.newspaper.like.repository.LikeRepository;
import etu.spb.etu.Internet.news.newspaper.post.dto.PostDto;
import etu.spb.etu.Internet.news.newspaper.post.dto.PostFullDto;
import etu.spb.etu.Internet.news.newspaper.post.dto.PostUpdateDto;
import etu.spb.etu.Internet.news.newspaper.post.model.Post;
import etu.spb.etu.Internet.news.newspaper.post.repository.PostRepository;
import etu.spb.etu.Internet.news.newspaper.post.service.PostService;
import etu.spb.etu.Internet.news.newspaper.user.model.User;
import etu.spb.etu.Internet.news.newspaper.user.repository.UserRepository;
import etu.spb.etu.Internet.news.newspaper.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@Profile("test")
public class PostServiceTest {

    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private JWTFilter jwtFilter;

    @MockBean
    private JWTUtil jwtUtil;

    @MockBean
    private AuthController authController;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private LikeRepository likeRepository;

    @Autowired
    private PostService postService;

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
    void createPost() {

        User user = User.builder()
                .id(1L)
                .name("max")
                .surname("Borodulin")
                .email("max@mail.ru")
                .password("12345")
                .build();

        PostDto postDto = PostDto.builder()
                .id(1L)
                .title("test")
                .description("test")
                .photoURL("photoUrl")
                .created(LocalDateTime.now())
                .userId(1L)
                .build();


        Post post = Post.builder()
                .id(1L)
                .title("test")
                .description("test")
                .photoURL("photoUrl")
                .userId(1L)
                .build();

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));
        when(postRepository.save(any()))
                .thenReturn(post);

        PostDto result = postService.createPost(postDto);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(post.getId());
        assertThat(result.getDescription()).isEqualTo(post.getDescription());
        assertThat(result.getCreated()).isEqualTo(post.getCreated());

        verify(userRepository, times(1)).findById(any());
        verify(postRepository, times(1)).save(any());
    }


    @Test
    void findAll() {
        User user = User.builder()
                .id(1L)
                .name("test")
                .surname("test")
                .password("12345")
                .email("test@test.com")
                .build();

        Comment comment = Comment.builder()
                .id(1L)
                .text("test")
                .user(user)
                .build();

        Post post = Post.builder()
                .id(1L)
                .title("test")
                .description("test")
                .photoURL("test")
                .userId(user.getId())
                .build();

        Like like = Like.builder()
                .id(1L)
                .post(post)
                .user(user)
                .build();


        when(postRepository.findById(anyLong()))
                .thenReturn(Optional.of(post));
        when(likeRepository.findAllByPostId(anyLong()))
                .thenReturn(Collections.singletonList(like));
        when(commentRepository.findAllByPostId(anyLong()))
                .thenReturn(Collections.singletonList(comment));

        PostFullDto result = postService.getById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(post.getId());
        assertThat(result.getTitle()).isEqualTo(post.getTitle());
        assertThat(result.getDescription()).isEqualTo(post.getDescription());
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void edit() {
        PostUpdateDto postUpdateDto = PostUpdateDto.builder()
                .id(1L)
                .title("дрель")
                .description("description")
                .photoURL("test")
                .build();


        Post post = Post.builder()
                .id(1L)
                .title("title")
                .description("description")
                .photoURL("test")
                .userId(authenticatedUser.getId())
                .build();

        when(userRepository.findByEmail(authenticatedUser.getEmail()))
                .thenReturn(Optional.of(authenticatedUser));

        when(postRepository.findById(anyLong()))
                .thenReturn(Optional.of(post));
        when(postRepository.save(any()))
                .thenReturn(post);

        PostDto result = postService.update(postUpdateDto, 1L);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(postUpdateDto.getId());
        assertThat(result.getTitle()).isEqualTo(postUpdateDto.getTitle());
        assertThat(result.getDescription()).isEqualTo(postUpdateDto.getDescription());

        verify(postRepository, times(1)).findById(any());
        verify(postRepository, times(1)).save(any());
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void deletePost() {
        PostDto postDto = PostDto.builder()
                .id(1L)
                .title("test")
                .description("test")
                .photoURL("photoUrl")
                .created(LocalDateTime.now())
                .userId(authenticatedUser.getId())
                .build();


        Post post = Post.builder()
                .id(1L)
                .title("test")
                .description("test")
                .photoURL("photoUrl")
                .userId(authenticatedUser.getId())
                .build();

        when(userRepository.findByEmail(authenticatedUser.getEmail()))
                .thenReturn(Optional.of(authenticatedUser));
        when(postRepository.findById(anyLong()))
                .thenReturn(Optional.of(post));
        when(postRepository.save(any()))
                .thenReturn(post);

        postService.deletePost(1L);
        verify(postRepository, times(1)).findById(anyLong());

    }

    @Test
    @WithMockUser(username = "test@example.com")
    void comment() {
        Post post = Post.builder()
                .id(1L)
                .title("title")
                .description("description")
                .photoURL("test")
                .userId(authenticatedUser.getId())
                .build();

        Comment comment = Comment.builder()
                .id(1L)
                .text("test")
                .user(authenticatedUser)
                .build();

        when(userRepository.findByEmail(authenticatedUser.getEmail()))
                .thenReturn(Optional.of(authenticatedUser));
        when(postRepository.findById(anyLong()))
                .thenReturn(Optional.of(post));
        when(commentRepository.save(any()))
                .thenReturn(comment);

        CommentDto result = postService.makeComment(1L, CommentUpdateDto.builder()
                .text("test")
                .build());

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getText()).isEqualTo("test");

        verify(postRepository, times(1)).findById(anyLong());
        verify(commentRepository, times(1)).save(any());
    }


    @Test
    void getByIdNotFoundException() {
        when(postRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> postService.getById(1L));
        verify(postRepository, times(1)).findById(anyLong());

    }

    @Test
    @WithMockUser(username = "test@example.com")
    void deleteComment() {
        Post post = Post.builder()
                .id(1L)
                .title("title")
                .description("description")
                .photoURL("test")
                .userId(authenticatedUser.getId())
                .build();

        Comment comment = Comment.builder()
                .id(1L)
                .text("test")
                .user(authenticatedUser)
                .build();

        when(userRepository.findByEmail(authenticatedUser.getEmail()))
                .thenReturn(Optional.of(authenticatedUser));
        when(commentRepository.findById(anyLong()))
                .thenReturn(Optional.of(comment));

        postService.deleteComment(1L);

        verify(commentRepository, times(1)).findById(anyLong());
    }

}
