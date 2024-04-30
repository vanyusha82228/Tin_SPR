package edu.java.scrapper.domain.jdbcInterface;

public interface TgChatService {
    void register(long tgChatId);

    void unregister(long tgChatId);

}
