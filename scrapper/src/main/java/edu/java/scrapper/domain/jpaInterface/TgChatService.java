package edu.java.scrapper.domain.jpaInterface;

public interface TgChatService {
    void register(long tgChatId);

    void unregister(long tgChatId);

}
