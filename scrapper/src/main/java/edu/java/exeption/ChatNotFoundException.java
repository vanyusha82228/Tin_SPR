package edu.java.exeption;

public class ChatNotFoundException extends RuntimeException {

    public ChatNotFoundException(String message) {
        super(message);
    }
}
