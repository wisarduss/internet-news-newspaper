package etu.spb.etu.Internet_news_newspaper.like.service;

import etu.spb.etu.Internet_news_newspaper.like.model.Like;

public interface LikeService {

    Like create(Like like);
    void deleteLike(Long likeId, Long userId);

}
