package etu.spb.etu.Internet.news.newspaper.post.service;

import etu.spb.etu.Internet.news.newspaper.comment.dto.CommentDto;
import etu.spb.etu.Internet.news.newspaper.comment.dto.CommentUpdateDto;
import etu.spb.etu.Internet.news.newspaper.post.dto.*;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);

    PostFullDto getById(Long id);

    PostDto update(PostUpdateDto postUpdateDto, Long id, Long userId);

    List<PostFullDto> getPosts();

    void deletePost(Long id, Long userId);

    CommentDto makeComment(Long postId, CommentUpdateDto text, Long userId);

    void deleteComment(Long commentId, Long userId);
}
