package etu.spb.etu.Internet_news_newspaper.post.service;

import etu.spb.etu.Internet_news_newspaper.exception.EmptyPostsException;
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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        userRepository.findById(post.getUserId())
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
    public List<PostFullDto> getPosts() {
        LocalDateTime startTime = LocalDateTime.now().minusHours(24);
        List<Post> posts = postRepository.findLatestPosts(startTime);
        List<CommentDto> comments;
        List<Like> likes;
        List<PostFullDto> fullPosts = new ArrayList<>();

        if (posts.isEmpty()) {
            throw new EmptyPostsException("К сожалению, за последнее день никто не выкладывал посты");
        }

        for (Post post : posts) {
             comments = commentRepository.findAllByPostId(post.getId()).stream()
                    .map(CommentMapper::commentToCommentDTO)
                    .collect(Collectors.toList());

             likes = likeRepository.findAllByPostId(post.getId());

             fullPosts.add(PostMapper.postToPostFullDto(post,comments,likes));
        }

        return fullPosts;

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
