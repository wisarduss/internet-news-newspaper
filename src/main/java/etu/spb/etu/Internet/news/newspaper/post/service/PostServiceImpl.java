package etu.spb.etu.Internet.news.newspaper.post.service;

import etu.spb.etu.Internet.news.newspaper.comment.dto.CommentDto;
import etu.spb.etu.Internet.news.newspaper.comment.dto.CommentUpdateDto;
import etu.spb.etu.Internet.news.newspaper.comment.mapper.CommentMapper;
import etu.spb.etu.Internet.news.newspaper.comment.model.Comment;
import etu.spb.etu.Internet.news.newspaper.comment.repository.CommentRepository;
import etu.spb.etu.Internet.news.newspaper.common.exception.EmptyPostsException;
import etu.spb.etu.Internet.news.newspaper.common.exception.IdNotFoundException;
import etu.spb.etu.Internet.news.newspaper.common.exception.NotOwnerException;
import etu.spb.etu.Internet.news.newspaper.like.dto.LikeDto;
import etu.spb.etu.Internet.news.newspaper.like.mapper.LikeMapper;
import etu.spb.etu.Internet.news.newspaper.like.repository.LikeRepository;
import etu.spb.etu.Internet.news.newspaper.post.dto.PostDto;
import etu.spb.etu.Internet.news.newspaper.post.dto.PostFullDto;
import etu.spb.etu.Internet.news.newspaper.post.dto.PostUpdateDto;
import etu.spb.etu.Internet.news.newspaper.post.mapper.PostMapper;
import etu.spb.etu.Internet.news.newspaper.post.model.Post;
import etu.spb.etu.Internet.news.newspaper.post.repository.PostRepository;
import etu.spb.etu.Internet.news.newspaper.user.model.User;
import etu.spb.etu.Internet.news.newspaper.user.repository.UserRepository;
import etu.spb.etu.Internet.news.newspaper.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final UserService userService;

    @Override
    @Transactional
    public PostDto createPost(PostDto postDto) {
        Post post = PostMapper.postDtoToPost(postDto);
        userRepository.findById(post.getUserId())
                .orElseThrow(() -> new IdNotFoundException("Пользователь с id = " + postDto.getUserId() + " не найден"));

        log.debug("Пост создан");
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
        List<LikeDto> likes = likeRepository.findAllByPostId(post.getId()).stream()
                .map(LikeMapper::likeToLikeDto)
                .collect(Collectors.toList());
        log.debug("Пост полчен");
        return PostMapper.postToPostFullDto(post, commentsDto, likes);
    }

    @Override
    @Transactional
    public PostDto update(PostUpdateDto postUpdateDto, Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Пост с id = " + id + " не найден"));

        User user = userService.getAuthenticatedUser();

        if (!Objects.equals(post.getUserId(), user.getId())) {
            throw new NotOwnerException("Пост может изменить владелец," +
                    " пользователь с id = " + user.getId() + " не является владельцем");
        }

        if (postUpdateDto.getTitle() != null) {
            post.setTitle(postUpdateDto.getTitle());
        }
        if (postUpdateDto.getDescription() != null) {
            post.setDescription(postUpdateDto.getDescription());
        }
        if (postUpdateDto.getPhotoURL() != null) {
            post.setPhotoURL(postUpdateDto.getPhotoURL());
        }
        log.debug("пост обновлен");
        return PostMapper.postToPostDto(postRepository.save(post));
    }

    @Override
    public List<PostFullDto> getPosts() {
        LocalDateTime startTime = LocalDateTime.now().minusHours(24);
        List<Post> posts = postRepository.findLatestPosts(startTime);
        List<CommentDto> comments;
        List<LikeDto> likes;
        List<PostFullDto> fullPosts = new ArrayList<>();

        if (posts.isEmpty()) {
            throw new EmptyPostsException("К сожалению, за последнее день никто не выкладывал посты");
        }

        for (Post post : posts) {
            comments = commentRepository.findAllByPostId(post.getId()).stream()
                    .map(CommentMapper::commentToCommentDTO)
                    .collect(Collectors.toList());

            likes = likeRepository.findAllByPostId(post.getId()).stream()
                    .map(LikeMapper::likeToLikeDto)
                    .collect(Collectors.toList());

            fullPosts.add(PostMapper.postToPostFullDto(post, comments, likes));
        }
        log.debug("Получены посты за 24 часа");
        return fullPosts;

    }

    @Override
    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Пост с id = " + id + " не найден"));

        User user = userService.getAuthenticatedUser();

        if (!Objects.equals(post.getUserId(), user.getId())) {
            throw new NotOwnerException("Только пользователь может удалить пост," +
                    " пользователь с id =" + user.getId() + " не является пользователем поста");
        }
        log.debug("Пост удален");
        postRepository.deleteById(id);
    }

    @Override
    @Transactional
    public CommentDto makeComment(Long postId, CommentUpdateDto text) {

        User user = userService.getAuthenticatedUser();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IdNotFoundException("Вещь с id = " + postId + " не найдена"));


        Comment comment = commentRepository.save(Comment.builder()
                .text(text.getText())
                .post(post)
                .user(user)
                .build());
        log.debug("клмментарий оставлен");
        return CommentMapper.commentToCommentDTO(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        User user = userService.getAuthenticatedUser();

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IdNotFoundException("Коммент с id = " + commentId + " не найден"));

        if (!Objects.equals(comment.getUser().getName(), user.getName())) {
            throw new NotOwnerException("Пользователь с id = " + user.getId() + " не является владельцем комментария," +
                    "комментарий может только удалить пользователь");
        }
        log.debug("комментарий удален");
        commentRepository.deleteById(commentId);
    }

}
