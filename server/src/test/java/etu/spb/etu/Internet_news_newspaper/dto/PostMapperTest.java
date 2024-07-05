package etu.spb.etu.Internet_news_newspaper.dto;

import etu.spb.etu.Internet_news_newspaper.like.model.Like;
import etu.spb.etu.Internet_news_newspaper.post.dto.CommentDto;
import etu.spb.etu.Internet_news_newspaper.post.dto.PostFullDto;
import etu.spb.etu.Internet_news_newspaper.post.mapper.PostMapper;
import etu.spb.etu.Internet_news_newspaper.post.model.Post;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PostMapperTest {

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

        List<CommentDto> comments = Collections.singletonList(comment);

        Like like = Like.builder()
                .id(1L)
                .postId(1L)
                .userId(1L)
                .build();

        List<Like> likes = Collections.singletonList(like);

        PostFullDto result = PostMapper.postToPostFullDto(post,comments,likes);
        assertThat(result.getId()).isEqualTo(post.getId());
        assertThat(result.getTitle()).isEqualTo(post.getTitle());
        assertThat(result.getDescription()).isEqualTo(post.getDescription());
        assertThat(result.getPhotoURL()).isEqualTo(post.getPhotoURL());
        assertThat(result.getComments()).usingRecursiveComparison().isEqualTo(comments);
        assertThat(result.getLikes()).usingRecursiveComparison().isEqualTo(likes);
    }
}
