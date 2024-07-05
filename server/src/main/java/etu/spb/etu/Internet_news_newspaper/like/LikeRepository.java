package etu.spb.etu.Internet_news_newspaper.like;

import etu.spb.etu.Internet_news_newspaper.like.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query("SELECT COUNT(l) > 0 FROM Like l WHERE l.userId = :userId AND l.postId = :postId")
    boolean existsByUserIdAndPostId(@Param("userId") Long userId, @Param("postId") Long postId);

    List<Like> findAllByPostId(Long postId);
}
