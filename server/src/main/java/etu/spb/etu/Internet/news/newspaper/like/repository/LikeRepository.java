package etu.spb.etu.Internet.news.newspaper.like.repository;

import etu.spb.etu.Internet.news.newspaper.like.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByUserIdAndPostId(Long userId, Long postId);

    List<Like> findAllByPostId(Long postId);
}
