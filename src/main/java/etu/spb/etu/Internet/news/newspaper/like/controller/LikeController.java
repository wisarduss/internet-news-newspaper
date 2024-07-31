package etu.spb.etu.Internet.news.newspaper.like.controller;

import etu.spb.etu.Internet.news.newspaper.like.dto.LikeDto;
import etu.spb.etu.Internet.news.newspaper.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @DeleteMapping("/{likeId}")
    public void deleteLike(@PathVariable Long likeId) {
        log.debug("Получен DELETE запрос на удаление лайка с likeId = {}",
                likeId);
        likeService.deleteLike(likeId);
    }
}
