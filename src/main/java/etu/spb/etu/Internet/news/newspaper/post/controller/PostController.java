package etu.spb.etu.Internet.news.newspaper.post.controller;

import etu.spb.etu.Internet.news.newspaper.like.service.LikeService;
import etu.spb.etu.Internet.news.newspaper.post.dto.PostDto;
import etu.spb.etu.Internet.news.newspaper.post.dto.PostFullDto;
import etu.spb.etu.Internet.news.newspaper.post.dto.PostUpdateDto;
import etu.spb.etu.Internet.news.newspaper.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

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

    @PatchMapping("/{id}")
    public PostDto update(@RequestBody PostUpdateDto postUpdateDto, @PathVariable Long id) {
        log.debug("Получен PATCH запрос на обновление поста " +
                "postDtoUpdate = {}, id = {}", postUpdateDto, id);
        return postService.update(postUpdateDto, id);
    }

    @GetMapping
    public List<PostFullDto> getPosts() {
        log.debug("Получен GET запрос на получение последних постов за 24 часа");
        return postService.getPosts();
    }

    @DeleteMapping("/{id}")
    public void deletePosts(@PathVariable Long id) {
        log.debug("Получен DELETE запрос на удаление поста по id = {}", id);
        postService.deletePost(id);
    }

}
