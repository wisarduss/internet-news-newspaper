package etu.spb.etu.Internet.news.newspaper.comment.mapper;

import etu.spb.etu.Internet.news.newspaper.comment.dto.CommentDto;
import etu.spb.etu.Internet.news.newspaper.comment.model.Comment;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CommentMapper {

    public static CommentDto commentToCommentDTO(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .userName(comment.getUser().getName())
                .createdComment(comment.getCreatedComment())
                .build();
    }
}
