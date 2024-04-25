package edu.java.domain.jdbc;

import edu.java.domain.model.Chat;
import edu.java.domain.repository.ChatRepository;
import edu.java.domain.jdbcInterface.TgChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
