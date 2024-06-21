package etu.spb.etu.Internet_news_newspaper.post.mapper;

import etu.spb.etu.Internet_news_newspaper.post.dto.PostDto;
import etu.spb.etu.Internet_news_newspaper.post.model.Post;
import etu.spb.etu.Internet_news_newspaper.post.model.PostUpdateDto;
import lombok.experimental.UtilityClass;

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

/*    public static PostUpdateDto postToPostUpdateDto(Post post) {
        return PostUpdateDto.builder()
                .title(post.getTitle())
                .description(post.getDescription())
                .photoURL(post.getPhotoURL())
                .build();
    }*/
}
