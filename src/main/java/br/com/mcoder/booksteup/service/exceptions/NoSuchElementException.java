package br.com.mcoder.booksteup.service.exceptions;

public class NoSuchElementException extends RuntimeException{
    public NoSuchElementException(String message) {
        super(message);
    }
}
