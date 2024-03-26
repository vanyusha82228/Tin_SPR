package edu.java.exeption;

public class ChatAlreadyRegisteredException extends RuntimeException {
    public ChatAlreadyRegisteredException(String message) {
        super(message);
    }
}
