package etu.spb.etu.Internet_news_newspaper.like.service;

import etu.spb.etu.Internet_news_newspaper.exception.AlreadyExistException;
import etu.spb.etu.Internet_news_newspaper.exception.IdNotFoundException;
import etu.spb.etu.Internet_news_newspaper.exception.NotOwnerException;
import etu.spb.etu.Internet_news_newspaper.like.model.Like;
import etu.spb.etu.Internet_news_newspaper.like.LikeRepository;
import etu.spb.etu.Internet_news_newspaper.post.model.Post;
import etu.spb.etu.Internet_news_newspaper.post.repository.PostRepository;
import etu.spb.etu.Internet_news_newspaper.user.UserRepository;
import etu.spb.etu.Internet_news_newspaper.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeServiceImpl implements LikeService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    @Override
    public Like create(Like like) {
        User user = userRepository.findById(like.getUserId())
                .orElseThrow(() -> new IdNotFoundException("Пользователь с id= " + like.getUserId() + " не найден"));

        Post post = postRepository.findById(like.getPostId())
                .orElseThrow(() -> new IdNotFoundException("Пост с id= " + like.getPostId() + " не найден"));

        if (isExist(like.getUserId(),like.getPostId())) {
            throw new AlreadyExistException("Пользователь с id= " + user.getId() + " поставил лайк");
        }
        log.debug("лайк поставлен");
        return likeRepository.save(like);
    }

    @Override
    public void deleteLike(Long likeId, Long userId) {
        Like like = likeRepository.findById(likeId)
                .orElseThrow(() -> new IdNotFoundException("Лайк c id = " + likeId + " не найден"));

        if (!Objects.equals(like.getUserId(), userId)) {
            throw new NotOwnerException("Лайк может удалить только пользователь, поставивший его," +
                    " пользователь с id = " + userId + " не является пользователем лайка");
        }
        log.debug("Лайк удален");
        likeRepository.deleteById(likeId);
    }


    private Boolean isExist(Long userId, Long postId) {
        return likeRepository.existsByUserIdAndPostId(userId,postId);
    }
}
