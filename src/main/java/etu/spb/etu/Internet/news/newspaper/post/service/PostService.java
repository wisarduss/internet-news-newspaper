package etu.spb.etu.Internet.news.newspaper.post.service;

import etu.spb.etu.Internet.news.newspaper.comment.dto.CommentDto;
import etu.spb.etu.Internet.news.newspaper.comment.dto.CommentUpdateDto;
import etu.spb.etu.Internet.news.newspaper.post.dto.PostDto;
import etu.spb.etu.Internet.news.newspaper.post.dto.PostFullDto;
import etu.spb.etu.Internet.news.newspaper.post.dto.PostUpdateDto;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);

    PostFullDto getById(Long id);

    PostDto update(PostUpdateDto postUpdateDto, Long id);

    List<PostFullDto> getPosts();

    void deletePost(Long id);

    CommentDto makeComment(Long postId, CommentUpdateDto text);

    void deleteComment(Long commentId);
}
