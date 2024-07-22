package etu.spb.etu.Internet.news.newspaper.post.mapper;

import etu.spb.etu.Internet.news.newspaper.comment.dto.CommentDto;
import etu.spb.etu.Internet.news.newspaper.like.dto.LikeDto;
import etu.spb.etu.Internet.news.newspaper.post.dto.PostDto;
import etu.spb.etu.Internet.news.newspaper.post.dto.PostFullDto;
import etu.spb.etu.Internet.news.newspaper.post.model.Post;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class PostMapper {

    public static Post postDtoToPost(PostDto postDto) {

        return Post.builder()
                .id(postDto.getId())
                .title(postDto.getTitle())
                .description(postDto.getDescription())
                .photoURL(postDto.getPhotoURL())
                .userId(postDto.getUserId())
                .build();
    }

    public static PostDto postToPostDto(Post post) {

        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .photoURL(post.getPhotoURL())
                .userId(post.getUserId())
                .created(post.getCreated())
                .build();
    }

    public static PostFullDto postToPostFullDto(Post post, List<CommentDto> comments, List<LikeDto> likes) {

        return PostFullDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .userId(post.getUserId())
                .photoURL(post.getPhotoURL())
                .created(post.getCreated())
                .comments(comments)
                .likes(likes)
                .build();
    }
}
