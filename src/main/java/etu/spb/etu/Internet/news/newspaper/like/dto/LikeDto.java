package etu.spb.etu.Internet.news.newspaper.like.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder(toBuilder = true)
public class LikeDto {

    private Long id;
    @NotNull
    private Long userId;
    @NotNull
    private Long postId;
}
