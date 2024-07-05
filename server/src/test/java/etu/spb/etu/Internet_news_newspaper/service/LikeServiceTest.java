package etu.spb.etu.Internet_news_newspaper.service;

import etu.spb.etu.Internet_news_newspaper.like.Like;
import etu.spb.etu.Internet_news_newspaper.like.LikeRepository;
import etu.spb.etu.Internet_news_newspaper.like.service.LikeService;
import etu.spb.etu.Internet_news_newspaper.post.model.Post;
import etu.spb.etu.Internet_news_newspaper.post.repository.PostRepository;
import etu.spb.etu.Internet_news_newspaper.user.UserRepository;
import etu.spb.etu.Internet_news_newspaper.user.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LikeServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private LikeRepository likeRepository;

    @Autowired
    private LikeService likeService;

    @Test
    void addLike() {
        User user = User.builder()
                .id(1L)
                .name("test")
                .surname("test")
                .password("12345")
                .email("test@test.com")
                .build();

        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(user));

        Post post = Post.builder()
                .id(1L)
                .title("title")
                .description("description")
                .photoURL("test")
                .userId(user.getId())
                .build();

        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));

        Like like = Like.builder()
                .id(1L)
                .postId(post.getId())
                .userId(user.getId())
                .build();

        when(likeRepository.save(any()))
                .thenReturn(like);

        Like result = likeRepository.save(like);

        assertThat(result).usingRecursiveComparison().isEqualTo(like);
        verify(likeRepository, times(1)).save(any());
    }

    @Test
    void deleteLike() {

        Like like = Like.builder()
                .id(1L)
                .postId(1L)
                .userId(1L)
                .build();

        when(likeRepository.findById(anyLong()))
                .thenReturn(Optional.of(like));

        likeService.deleteLike(1L, 1L);
        verify(likeRepository, times(1)).deleteById(anyLong());
    }
}
