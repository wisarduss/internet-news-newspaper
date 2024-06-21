package etu.spb.etu.Internet_news_newspaper.post.service;

import etu.spb.etu.Internet_news_newspaper.exception.IdNotFoundException;
import etu.spb.etu.Internet_news_newspaper.post.PostRepository;
import etu.spb.etu.Internet_news_newspaper.post.dto.PostDto;
import etu.spb.etu.Internet_news_newspaper.post.mapper.PostMapper;
import etu.spb.etu.Internet_news_newspaper.post.model.Post;
import etu.spb.etu.Internet_news_newspaper.post.model.PostUpdateDto;
import etu.spb.etu.Internet_news_newspaper.user.UserRepository;
import etu.spb.etu.Internet_news_newspaper.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = PostMapper.postDtoToPost(postDto);
        User postUser = userRepository.findById(post.getUserId())
                .orElseThrow(() -> new IdNotFoundException("Пользователь с id = " + postDto.getUserId() + " не найден"));

        return PostMapper.postToPostDto(postRepository.save(post));
    }

    @Override
    public PostDto getById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Пост с id = " + id + " не найден"));
        return PostMapper.postToPostDto(post);
    }

    @Override
    public PostDto update(PostUpdateDto postUpdateDto, Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Пост с id = " + id + " не найден"));

        if (postUpdateDto.getTitle() != null) {
            post.setTitle(postUpdateDto.getTitle());
        }
        if (postUpdateDto.getDescription() != null) {
            post.setDescription(postUpdateDto.getDescription());
        }
        if (postUpdateDto.getPhotoURL() != null) {
            post.setPhotoURL(postUpdateDto.getPhotoURL());
        }

        return PostMapper.postToPostDto(postRepository.save(post));
    }
}
