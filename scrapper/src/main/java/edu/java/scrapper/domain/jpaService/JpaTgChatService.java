package edu.java.scrapper.domain.jpaService;


import edu.java.scrapper.domain.jdbcInterface.TgChatService;
import edu.java.scrapper.domain.jpaRepository.JpaChatRepository;
import edu.java.scrapper.domain.model.Chat;
import org.springframework.beans.factory.annotation.Autowired;


public class JpaTgChatService implements TgChatService {
    private final JpaChatRepository chatRepository;

    @Autowired
    public JpaTgChatService(JpaChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public void register(long tgChatId) {
        Chat chat = chatRepository.findById(tgChatId).orElse(null);
        chatRepository.save(chat);
    }

    @Override
    public void unregister(long tgChatId) {
        Chat chat = chatRepository.findById(tgChatId).orElse(null);
        if (chat != null) {
            chatRepository.delete(chat);
        }
    }
}
