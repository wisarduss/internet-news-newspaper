package etu.spb.etu.Internet_news_newspaper.post;

import etu.spb.etu.Internet_news_newspaper.like.model.Like;
import etu.spb.etu.Internet_news_newspaper.like.service.LikeService;
import etu.spb.etu.Internet_news_newspaper.post.dto.*;
import etu.spb.etu.Internet_news_newspaper.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;
    private final LikeService likeService;

    @PostMapping
    public PostDto create(@RequestBody PostDto postDto) {
        log.debug("Получен POST запрос на создание поста {}", postDto);
        return postService.createPost(postDto);
    }

    @GetMapping("/{id}")
    public PostFullDto getPostById(@PathVariable Long id) {
        log.debug("Получен GET запрос на получение поста по id = {}", id);
        return postService.getById(id);
    }

    @PatchMapping("/{id}/{userId}")
    public PostDto update(@RequestBody PostUpdateDto postUpdateDto, @PathVariable Long id, @PathVariable Long userId) {
        log.debug("Получен PATCH запрос на обновление поста " +
                "postDtoUpdate = {}, id = {}, userId = {}",postUpdateDto,id,userId);
        return postService.update(postUpdateDto, id, userId);
    }

    @GetMapping
    public List<PostFullDto> getPosts() {
        log.debug("Получен GET запрос на получение последних постов за 24 часа");
       return postService.getPosts();
    }

    @DeleteMapping("/{id}/{userId}")
    public void deletePosts(@PathVariable Long id, @PathVariable Long userId) {
        log.debug("Получен DELETE запрос на удаление поста по id = {}, userId = {}", id, userId);
        postService.deletePost(id, userId);
    }

    @PostMapping("/{postId}/comment/{userId}")
    public CommentDto comment(
            @PathVariable Long postId,
            @RequestBody @Valid CommentUpdateDto text,
            @PathVariable Long userId) {
        log.debug("Получен POST запрос на добавление комментария = {} к посту postId = {}" +
                " от пользователя userId = {}", text, postId, userId);
        return postService.makeComment(postId, text, userId);
    }

    @DeleteMapping("/comment/{commentId}/{userId}")
    void deleteComment(@PathVariable Long commentId, @PathVariable Long userId) {
        log.debug("Получен DELETE запрос на удаление комментария с id = {} от пользователя userId = {}",
                commentId, userId);
        postService.deleteComment(commentId, userId);
    }

    @PostMapping("/like")
    public Like like(@RequestBody Like like) {
        log.debug("Получен POST запрос на добавление лайка {}", like);
        return likeService.create(like);
    }

    @DeleteMapping("/like/{likeId}/{userId}")
    public void deleteLike(@PathVariable Long likeId, @PathVariable Long userId) {
        log.debug("Получен DELETE запрос на удаление лайка с likeId = {} от пользователя с userId = {}",
                likeId, userId);
        likeService.deleteLike(likeId, userId);
    }

}
