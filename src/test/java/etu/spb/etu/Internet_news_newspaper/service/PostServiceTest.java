package etu.spb.etu.Internet_news_newspaper.service;


import etu.spb.etu.Internet_news_newspaper.like.Like;
import etu.spb.etu.Internet_news_newspaper.like.LikeRepository;
import etu.spb.etu.Internet_news_newspaper.post.dto.*;
import etu.spb.etu.Internet_news_newspaper.post.mapper.CommentMapper;
import etu.spb.etu.Internet_news_newspaper.post.model.Comment;
import etu.spb.etu.Internet_news_newspaper.post.model.Post;
import etu.spb.etu.Internet_news_newspaper.post.repository.CommentRepository;
import etu.spb.etu.Internet_news_newspaper.post.repository.PostRepository;
import etu.spb.etu.Internet_news_newspaper.post.service.PostService;
import etu.spb.etu.Internet_news_newspaper.user.UserRepository;
import etu.spb.etu.Internet_news_newspaper.user.dto.UserDto;
import etu.spb.etu.Internet_news_newspaper.user.model.User;
import javafx.geometry.Pos;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PostServiceTest {

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
                .postId(post.getId())
                .userId(user.getId())
                .build();


        when(postRepository.findById(anyLong()))
                .thenReturn(Optional.of(post));
        when(likeRepository.findAllByPostId(anyLong()))
                .thenReturn(Collections.singletonList(like));
        when(commentRepository.findAllByPostId(anyLong()))
                .thenReturn(Collections.singletonList(comment));

        PostFullDto result =postService.getById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(post.getId());
        assertThat(result.getTitle()).isEqualTo(post.getTitle());
        assertThat(result.getDescription()).isEqualTo(post.getDescription());
        assertThat(result.getComments()).hasSize(1);
        assertThat(result.getComments().get(0).getId()).isEqualTo(comment.getId());
        assertThat(result.getComments().get(0).getText()).isEqualTo(comment.getText());
    }

    @Test
    void edit() {

        PostUpdateDto postUpdateDto = PostUpdateDto.builder()
                .id(1L)
                .title("дрель")
                .description("description")
                .photoURL("test")
                .build();

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

        when(postRepository.getById(anyLong())).thenReturn(post);
        when(postRepository.save(any())).thenReturn(post);

        PostDto result = postService.update(postUpdateDto, 1L);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(postUpdateDto.getId());
        assertThat(result.getTitle()).isEqualTo(postUpdateDto.getTitle());
        assertThat(result.getDescription()).isEqualTo(postUpdateDto.getDescription());

        verify(postRepository, times(1)).getById(any());
        verify(postRepository, times(1)).save(any());
    }



    @Test
    void comment() {
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

        Comment comment = Comment.builder()
                .id(1L)
                .text("test")
                .user(user)
                .build();


        when(postRepository.findById(anyLong()))
                .thenReturn(Optional.of(post));
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(user));
        when(commentRepository.save(any()))
                .thenReturn(comment);

        CommentDto result = postService.makeComment(1L, CommentUpdateDto.builder()
                .text("test")
                .build(), 1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getText()).isEqualTo("test");
        assertThat(result.getUserName()).isEqualTo("test");

        verify(postRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).findById(anyLong());
        verify(commentRepository, times(1)).save(any());
    }

}
