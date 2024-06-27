package etu.spb.etu.Internet_news_newspaper.post.service;

import etu.spb.etu.Internet_news_newspaper.exception.IdNotFoundException;
import etu.spb.etu.Internet_news_newspaper.like.Like;
import etu.spb.etu.Internet_news_newspaper.like.LikeRepository;
import etu.spb.etu.Internet_news_newspaper.post.dto.*;
import etu.spb.etu.Internet_news_newspaper.post.mapper.CommentMapper;
import etu.spb.etu.Internet_news_newspaper.post.model.Comment;
import etu.spb.etu.Internet_news_newspaper.post.repository.CommentRepository;
import etu.spb.etu.Internet_news_newspaper.post.repository.PostRepository;
import etu.spb.etu.Internet_news_newspaper.post.mapper.PostMapper;
import etu.spb.etu.Internet_news_newspaper.post.model.Post;
import etu.spb.etu.Internet_news_newspaper.user.UserRepository;
import etu.spb.etu.Internet_news_newspaper.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = PostMapper.postDtoToPost(postDto);
        User postUser = userRepository.findById(post.getUserId())
                .orElseThrow(() -> new IdNotFoundException("Пользователь с id = " + postDto.getUserId() + " не найден"));

        return PostMapper.postToPostDto(postRepository.save(post));
    }

    @Override
    public PostFullDto getById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Пост с id = " + id + " не найден"));

        List<Comment> comments = commentRepository.findAllByPostId(post.getId());
        List<CommentDto> commentsDto = comments.stream()
                .map(CommentMapper::commentToCommentDTO)
                .collect(Collectors.toList());
        List<Like> likes = likeRepository.findAllByPostId(post.getId());
        return PostMapper.postToPostFullDto(post,commentsDto, likes);
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

    @Override
    public List<PostDto> getThreeLastPosts(Pageable pageable) {
        List<Post> result = postRepository.findTop3ByOrderByCreatedDesc(pageable);
        return result.stream()
                .map(PostMapper::postToPostDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto makeComment(Long postId, CommentUpdateDto text, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IdNotFoundException("Пользователь с id = " + userId + " не найден"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IdNotFoundException("Вещь с id = " + postId + " не найдена"));


        Comment comment = commentRepository.save(Comment.builder()
                .text(text.getText())
                .post(post)
                .user(user)
                .build());
        return CommentMapper.commentToCommentDTO(comment);
    }
}
