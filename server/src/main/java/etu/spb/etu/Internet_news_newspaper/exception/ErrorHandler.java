package etu.spb.etu.Internet_news_newspaper.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error("Ошибка валидации данных из запроса.")
                .build();
        log.debug("{}: {}", MethodArgumentNotValidException.class.getSimpleName(),
                e.getFieldError().getDefaultMessage());

        return errorResponse;
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IdNotFoundException.class)
    public ErrorResponse handleValidationExceptions(IdNotFoundException ex) {
        log.debug("Получен статус 404 not found {}", ex.getMessage());
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleObjectDoesNotExistException(final AlreadyExistException e) {
        log.debug("Получен статус 500 internal server error found {}", e.getMessage());
        return new ErrorResponse(e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EmptyPostsException.class)
    public ErrorResponse handleValidationExceptions(EmptyPostsException ex) {
        log.debug("Получен статус 404 not found {}", ex.getMessage());
        return new ErrorResponse(ex.getMessage());

    }
}
