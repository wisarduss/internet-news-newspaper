package etu.spb.etu.Internet_news_newspaper.post.repository;

import etu.spb.etu.Internet_news_newspaper.post.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    List<Post> findTop3ByOrderByCreatedDesc(Pageable pageable);

}
