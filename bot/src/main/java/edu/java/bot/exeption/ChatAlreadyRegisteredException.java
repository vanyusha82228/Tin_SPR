package edu.java.bot.exeption;

public class ChatAlreadyRegisteredException extends RuntimeException {
    public ChatAlreadyRegisteredException(String message) {
        super(message);
    }
}
