package etu.spb.etu.Internet_news_newspaper.post.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;

@Data
@Builder(toBuilder = true)
@Jacksonized
public class CommentUpdateDto {

    @NotBlank
    private String text;
}
