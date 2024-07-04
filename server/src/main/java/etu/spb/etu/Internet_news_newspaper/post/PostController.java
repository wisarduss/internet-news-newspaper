package etu.spb.etu.Internet_news_newspaper.post;

import etu.spb.etu.Internet_news_newspaper.like.Like;
import etu.spb.etu.Internet_news_newspaper.like.service.LikeService;
import etu.spb.etu.Internet_news_newspaper.post.dto.*;
import etu.spb.etu.Internet_news_newspaper.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final LikeService likeService;

    @PostMapping
    public PostDto create(@RequestBody PostDto postDto) {
        return postService.createPost(postDto);
    }

    @GetMapping("/{id}")
    public PostFullDto getPostById(@PathVariable Long id) {
        return postService.getById(id);
    }

    @PatchMapping("/{id}")
    public PostDto update(@RequestBody PostUpdateDto postUpdateDto, @PathVariable Long id) {
        return postService.update(postUpdateDto, id);
    }

    @GetMapping
    public List<PostFullDto> getPosts() {
       return postService.getPosts();
    }

    @PostMapping("/{postId}/comment/{userId}")
    public CommentDto comment(
            @PathVariable Long postId,
            @RequestBody @Valid CommentUpdateDto text,
            @PathVariable Long userId) {

        return postService.makeComment(postId, text, userId);
    }

    @PostMapping("/like")
    public Like like(@RequestBody Like like) {
        return likeService.create(like);
    }

    @DeleteMapping("/like/{likeId}")
    public void deleteLike(@PathVariable Long likeId) {
        likeService.deleteLike(likeId);
    }


}
