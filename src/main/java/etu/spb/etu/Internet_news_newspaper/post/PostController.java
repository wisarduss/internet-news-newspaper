package etu.spb.etu.Internet_news_newspaper.post;

import etu.spb.etu.Internet_news_newspaper.post.dto.PostDto;
import etu.spb.etu.Internet_news_newspaper.post.model.PostUpdateDto;
import etu.spb.etu.Internet_news_newspaper.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public PostDto create(@RequestBody PostDto postDto) {
        return postService.createPost(postDto);
    }

    @GetMapping("/{id}")
    public PostDto getPostById(@PathVariable Long id) {
        return postService.getById(id);
    }

    @PatchMapping("/{id}")
    public PostDto update(@RequestBody PostUpdateDto postUpdateDto, @PathVariable Long id) {
        return postService.update(postUpdateDto, id);
    }
}
