package etu.spb.etu.Internet_news_newspaper.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ErrorResponse {

    private String error;
}
