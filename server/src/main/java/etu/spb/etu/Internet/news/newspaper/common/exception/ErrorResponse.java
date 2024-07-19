package etu.spb.etu.Internet.news.newspaper.common.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ErrorResponse {

    private String error;
}
