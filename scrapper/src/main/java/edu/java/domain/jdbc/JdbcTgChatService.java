package edu.java.domain.jdbc;

import edu.java.domain.dao.ChatDao;
import edu.java.domain.jdbcInterface.TgChatService;
import edu.java.domain.model.Chat;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JdbcTgChatService implements TgChatService {
    private final ChatDao chatDao;

    @Autowired
    public JdbcTgChatService(ChatDao chatDao) {
        this.chatDao = chatDao;
    }

    @Override
    public void register(long tgChatId) {
        Chat chat = new Chat();
        chat.setId(tgChatId);
        chat.setCreatedAt(LocalDateTime.now());
        chatDao.add(chat);
    }

    @Override
    public void unregister(long tgChatId) {
        chatDao.remove(tgChatId);
    }
}
