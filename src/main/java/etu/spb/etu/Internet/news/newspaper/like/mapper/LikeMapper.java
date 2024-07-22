package etu.spb.etu.Internet.news.newspaper.like.mapper;

import etu.spb.etu.Internet.news.newspaper.like.dto.LikeDto;
import etu.spb.etu.Internet.news.newspaper.like.model.Like;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LikeMapper {

    public static LikeDto likeToLikeDto(Like like) {
        return LikeDto.builder()
                .id(like.getId())
                .userId(like.getUser().getId())
                .postId(like.getPost().getId())
                .build();
    }

}
