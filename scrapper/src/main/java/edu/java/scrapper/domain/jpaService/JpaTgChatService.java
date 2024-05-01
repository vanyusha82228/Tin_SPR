package edu.java.scrapper.domain.jpaService;

import edu.java.scrapper.domain.jdbcInterface.TgChatService;
import edu.java.scrapper.domain.jpaRepository.JpaChatRepository;
import org.springframework.beans.factory.annotation.Autowired;


public class JpaTgChatService implements TgChatService {
    private final JpaChatRepository chatRepository;

    @Autowired
    public JpaTgChatService(JpaChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public void register(long tgChatId) {
        chatRepository.findById(tgChatId).ifPresent(chatRepository::save);
    }

    @Override
    public void unregister(long tgChatId) {
        chatRepository.findById(tgChatId).ifPresent(chatRepository::delete);
    }
}
