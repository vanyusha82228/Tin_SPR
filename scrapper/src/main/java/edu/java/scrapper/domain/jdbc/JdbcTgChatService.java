package edu.java.scrapper.domain.jdbc;


import edu.java.scrapper.domain.jdbcInterface.TgChatService;
import edu.java.scrapper.domain.model.Chat;
import edu.java.scrapper.domain.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class JdbcTgChatService implements TgChatService {
    private final ChatRepository chatRepository;

    @Autowired
    public JdbcTgChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public void register(long tgChatId) {
        Chat chat = chatRepository.findById(tgChatId);
        chatRepository.add(chat);
    }

    @Override
    public void unregister(long tgChatId) {
        Chat chat = chatRepository.findById(tgChatId);
        chatRepository.remove(chat.getId());
    }
}
