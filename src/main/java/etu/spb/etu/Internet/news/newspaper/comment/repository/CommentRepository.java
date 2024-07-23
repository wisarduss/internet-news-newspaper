package etu.spb.etu.Internet.news.newspaper.comment.repository;

import etu.spb.etu.Internet.news.newspaper.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostId(Long postId);
}
