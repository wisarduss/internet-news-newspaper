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
                .orElseThrow(() -> new IdNotFoundException(
                        String.format("Пользователь с id = %d не найден", likeDto.getUserId())));

        Post post = postRepository.findById(likeDto.getPostId())
                .orElseThrow(() -> new IdNotFoundException(
                        String.format("Пост c id = %d не найден", likeDto.getPostId())));

        if (isExist(likeDto.getUserId(), likeDto.getPostId())) {
            throw new AlreadyExistException(String.format("Пользователь с id = %d уже поставил лайк", user.getId()));
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
                .orElseThrow(() -> new IdNotFoundException(String.format("Лайк с id = %d не найден", likeId)));
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
