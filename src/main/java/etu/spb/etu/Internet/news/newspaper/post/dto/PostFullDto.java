package etu.spb.etu.Internet.news.newspaper.post.dto;

import etu.spb.etu.Internet.news.newspaper.comment.dto.CommentDto;
import etu.spb.etu.Internet.news.newspaper.like.dto.LikeDto;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder(toBuilder = true)
public class PostFullDto {

    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String photoURL;
    private LocalDateTime created;
    @NotNull
    private Long userId;
    private List<CommentDto> comments;
    private List<LikeDto> likes;
}
