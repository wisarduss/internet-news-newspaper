package etu.spb.etu.Internet.news.newspaper.common.exception;

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
