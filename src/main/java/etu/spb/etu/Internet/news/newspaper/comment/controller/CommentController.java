package etu.spb.etu.Internet.news.newspaper.comment.controller;

import etu.spb.etu.Internet.news.newspaper.comment.dto.CommentDto;
import etu.spb.etu.Internet.news.newspaper.comment.dto.CommentUpdateDto;
import etu.spb.etu.Internet.news.newspaper.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;

import javax.validation.Valid;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final PostService postService;

    @PostMapping("/{postId}")
    public CommentDto comment(
            @PathVariable Long postId,
            @RequestBody @Valid CommentUpdateDto text) {
        log.debug("Получен POST запрос на добавление комментария = {} к посту postId = {}", text, postId);
        return postService.makeComment(postId, text);
    }

    @DeleteMapping("/{commentId}")
    void deleteComment(@PathVariable Long commentId) {
        log.debug("Получен DELETE запрос на удаление комментария с id = {}",
                commentId);
        postService.deleteComment(commentId);
    }
}
