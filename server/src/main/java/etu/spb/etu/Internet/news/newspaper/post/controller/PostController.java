package etu.spb.etu.Internet.news.newspaper.post.controller;

import etu.spb.etu.Internet.news.newspaper.like.service.LikeService;
import etu.spb.etu.Internet.news.newspaper.like.model.Like;
import etu.spb.etu.Internet.news.newspaper.post.dto.*;
import etu.spb.etu.Internet.news.newspaper.post.service.PostService;
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

}
