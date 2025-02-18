package br.com.mcoder.booksteup.service.exceptions;

public class DatabaseException extends RuntimeException{
    public DatabaseException(String message){
        super(message);
    }
}
