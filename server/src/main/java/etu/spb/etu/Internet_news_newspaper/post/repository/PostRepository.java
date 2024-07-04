package etu.spb.etu.Internet_news_newspaper.post.repository;

import etu.spb.etu.Internet_news_newspaper.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    @Query("SELECT p FROM Post p WHERE p.created >= :startTime")
    List<Post> findLatestPosts(@Param("startTime") LocalDateTime startTime);

}
