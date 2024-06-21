package etu.spb.etu.Internet_news_newspaper.post.service;

import etu.spb.etu.Internet_news_newspaper.post.dto.PostDto;
import etu.spb.etu.Internet_news_newspaper.post.model.PostUpdateDto;

public interface PostService {

    PostDto createPost(PostDto postDto);

    PostDto getById(Long id);

    PostDto update(PostUpdateDto postUpdateDto, Long id);
}
