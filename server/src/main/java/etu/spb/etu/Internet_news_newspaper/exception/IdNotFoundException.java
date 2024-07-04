package etu.spb.etu.Internet_news_newspaper.exception;

public class IdNotFoundException extends RuntimeException{
    public IdNotFoundException() {
        super();
    }

    public IdNotFoundException(String message) {
        super(message);
    }

    public IdNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
