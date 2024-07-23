package etu.spb.etu.Internet.news.newspaper.dto;

import etu.spb.etu.Internet.news.newspaper.authentication.AuthController;
import etu.spb.etu.Internet.news.newspaper.authentication.config.JWTFilter;
import etu.spb.etu.Internet.news.newspaper.authentication.service.AuthenticationService;
import etu.spb.etu.Internet.news.newspaper.comment.dto.CommentDto;
import etu.spb.etu.Internet.news.newspaper.common.util.JWTUtil;
import etu.spb.etu.Internet.news.newspaper.like.dto.LikeDto;
import etu.spb.etu.Internet.news.newspaper.post.dto.PostFullDto;
import etu.spb.etu.Internet.news.newspaper.post.mapper.PostMapper;
import etu.spb.etu.Internet.news.newspaper.post.model.Post;
import etu.spb.etu.Internet.news.newspaper.user.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Profile("test")
public class PostMapperTest {

    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private JWTFilter jwtFilter;

    @MockBean
    private JWTUtil jwtUtil;

    @MockBean
    private AuthController authController;

    @Test
    void toFullDtoAllFieldsTest() {

        Post post = Post.builder()
                .id(1L)
                .title("title")
                .description("description")
                .photoURL("test")
                .userId(1L)
                .build();

        CommentDto comment = CommentDto.builder()
                .id(1L)
                .text("text")
                .userName("authorName")
                .createdComment(LocalDateTime.now())
                .build();

        User user = User.builder()
                .id(1L)
                .name("test")
                .surname("test")
                .password("12345")
                .email("test@test.com")
                .build();

        List<CommentDto> comments = Collections.singletonList(comment);

        LikeDto likeDto = LikeDto.builder()
                .id(1L)
                .postId(1L)
                .userId(1L)
                .build();

        List<LikeDto> likes = Collections.singletonList(likeDto);

        PostFullDto result = PostMapper.postToPostFullDto(post, comments, likes);
        assertThat(result.getId()).isEqualTo(post.getId());
        assertThat(result.getTitle()).isEqualTo(post.getTitle());
        assertThat(result.getDescription()).isEqualTo(post.getDescription());
        assertThat(result.getPhotoURL()).isEqualTo(post.getPhotoURL());
        assertThat(result.getComments()).usingRecursiveComparison().isEqualTo(comments);
        assertThat(result.getLikes()).usingRecursiveComparison().isEqualTo(likes);
    }
}
