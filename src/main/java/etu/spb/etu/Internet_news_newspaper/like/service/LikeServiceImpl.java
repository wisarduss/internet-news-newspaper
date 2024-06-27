package etu.spb.etu.Internet_news_newspaper.like.service;


import etu.spb.etu.Internet_news_newspaper.exception.AlreadyExistException;
import etu.spb.etu.Internet_news_newspaper.exception.IdNotFoundException;
import etu.spb.etu.Internet_news_newspaper.like.Like;
import etu.spb.etu.Internet_news_newspaper.like.LikeRepository;
import etu.spb.etu.Internet_news_newspaper.post.model.Post;
import etu.spb.etu.Internet_news_newspaper.post.repository.PostRepository;
import etu.spb.etu.Internet_news_newspaper.user.UserRepository;
import etu.spb.etu.Internet_news_newspaper.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
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

        return likeRepository.save(like);
    }

    @Override
    public void deleteLike(Long likeId) {
        Like like = likeRepository.findById(likeId)
                .orElseThrow(() -> new IdNotFoundException("Лайк c id = " + likeId + " не найден"));
        likeRepository.deleteById(likeId);
    }


    private Boolean isExist(Long userId, Long postId) {
        return likeRepository.existsByUserIdAndPostId(userId,postId);
    }
}
