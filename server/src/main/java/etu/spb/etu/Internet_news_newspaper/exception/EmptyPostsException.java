package etu.spb.etu.Internet_news_newspaper.exception;

public class EmptyPostsException extends RuntimeException {

    public EmptyPostsException() {
        super();
    }

    public EmptyPostsException(String message) {
        super(message);
    }

    public EmptyPostsException(String message, Throwable cause) {
        super(message, cause);
    }
}
