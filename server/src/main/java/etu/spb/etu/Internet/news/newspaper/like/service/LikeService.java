package etu.spb.etu.Internet.news.newspaper.like.service;

import etu.spb.etu.Internet.news.newspaper.like.dto.LikeDto;
import etu.spb.etu.Internet.news.newspaper.like.model.Like;

public interface LikeService {

    LikeDto create(LikeDto likeDto);
    void deleteLike(Long likeId, Long userId);

}
