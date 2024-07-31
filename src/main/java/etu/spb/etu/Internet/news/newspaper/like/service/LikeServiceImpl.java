package etu.spb.etu.Internet.news.newspaper.like.service;

import etu.spb.etu.Internet.news.newspaper.common.exception.AlreadyExistException;
import etu.spb.etu.Internet.news.newspaper.common.exception.IdNotFoundException;
import etu.spb.etu.Internet.news.newspaper.common.exception.NotOwnerException;
import etu.spb.etu.Internet.news.newspaper.like.dto.LikeDto;
import etu.spb.etu.Internet.news.newspaper.like.mapper.LikeMapper;
import etu.spb.etu.Internet.news.newspaper.like.model.Like;
import etu.spb.etu.Internet.news.newspaper.like.repository.LikeRepository;
import etu.spb.etu.Internet.news.newspaper.post.model.Post;
import etu.spb.etu.Internet.news.newspaper.post.repository.PostRepository;
import etu.spb.etu.Internet.news.newspaper.user.model.User;
import etu.spb.etu.Internet.news.newspaper.user.repository.UserRepository;
import etu.spb.etu.Internet.news.newspaper.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LikeServiceImpl implements LikeService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final UserService userService;

    @Override
    public LikeDto create(LikeDto likeDto) {
        User user = userRepository.findById(likeDto.getUserId())
                .orElseThrow(() -> new IdNotFoundException("Пользователь с id= " + likeDto.getUserId() + " не найден"));

        Post post = postRepository.findById(likeDto.getPostId())
                .orElseThrow(() -> new IdNotFoundException("Пост с id= " + likeDto.getPostId() + " не найден"));

        if (isExist(likeDto.getUserId(), likeDto.getPostId())) {
            throw new AlreadyExistException("Пользователь с id= " + user.getId() + " поставил лайк");
        }

        Like like = Like.builder()
                .user(user)
                .post(post)
                .build();

        log.debug("лайк поставлен");
        return LikeMapper.likeToLikeDto(likeRepository.save(like));
    }

    @Override
    public void deleteLike(Long likeId) throws NotOwnerException {
        Like like = likeRepository.findById(likeId)
                .orElseThrow(() -> new IdNotFoundException("Лайк c id = " + likeId + " не найден"));
        User user = userService.getAuthenticatedUser();

        if (!like.getUser().equals(user)) {
            throw new NotOwnerException("Лайк может удалить только пользователь, поставивший его");
        }
        log.debug("Лайк удален");
        likeRepository.deleteById(likeId);
    }


    private Boolean isExist(Long userId, Long postId) {
        return likeRepository.existsByUserIdAndPostId(userId, postId);
    }
}
