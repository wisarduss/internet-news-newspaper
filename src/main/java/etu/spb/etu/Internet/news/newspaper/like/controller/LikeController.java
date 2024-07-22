package etu.spb.etu.Internet.news.newspaper.like.controller;

import etu.spb.etu.Internet.news.newspaper.like.dto.LikeDto;
import etu.spb.etu.Internet.news.newspaper.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
@Slf4j
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public LikeDto like(@RequestBody LikeDto likeDto) {
        log.debug("Получен POST запрос на добавление лайка {}", likeDto);
        return likeService.create(likeDto);
    }

    @DeleteMapping("/{likeId}/{userId}")
    public void deleteLike(@PathVariable Long likeId, @PathVariable Long userId) {
        log.debug("Получен DELETE запрос на удаление лайка с likeId = {} от пользователя с userId = {}",
                likeId, userId);
        likeService.deleteLike(likeId, userId);
    }
}
