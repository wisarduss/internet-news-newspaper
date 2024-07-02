package etu.spb.etu.Internet_news_newspaper.post.service;

import etu.spb.etu.Internet_news_newspaper.post.dto.*;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);

    PostFullDto getById(Long id);

    PostDto update(PostUpdateDto postUpdateDto, Long id);

    List<PostFullDto> getPosts();

    CommentDto makeComment(Long postId, CommentUpdateDto text, Long userId);
}
