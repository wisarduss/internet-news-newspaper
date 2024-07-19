package etu.spb.etu.Internet.news.newspaper.common.exception;

public class NotOwnerException extends RuntimeException {

    public NotOwnerException() {
        super();
    }

    public NotOwnerException(String message) {
        super(message);
    }

    public NotOwnerException(String message, Throwable cause) {
        super(message, cause);
    }
}
