package etu.spb.etu.Internet.news.newspaper.comment.controller;

import etu.spb.etu.Internet.news.newspaper.comment.dto.CommentDto;
import etu.spb.etu.Internet.news.newspaper.comment.dto.CommentUpdateDto;
import etu.spb.etu.Internet.news.newspaper.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final PostService postService;

    @PostMapping("/{postId}/{userId}")
    public CommentDto comment(
            @PathVariable Long postId,
            @RequestBody @Valid CommentUpdateDto text,
            @PathVariable Long userId) {
        log.debug("Получен POST запрос на добавление комментария = {} к посту postId = {}" +
                " от пользователя userId = {}", text, postId, userId);
        return postService.makeComment(postId, text, userId);
    }

    @DeleteMapping("/{commentId}/{userId}")
    void deleteComment(@PathVariable Long commentId, @PathVariable Long userId) {
        log.debug("Получен DELETE запрос на удаление комментария с id = {} от пользователя userId = {}",
                commentId, userId);
        postService.deleteComment(commentId, userId);
    }
}
