package etu.spb.etu.Internet_news_newspaper.post.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
public class CommentDto {

    private Long id;
    private String text;
    private String userName;
    private LocalDateTime createdComment;
}
