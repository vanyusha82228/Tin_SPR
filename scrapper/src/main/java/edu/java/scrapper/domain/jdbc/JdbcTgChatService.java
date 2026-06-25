package edu.java.scrapper.domain.jdbc;


import edu.java.scrapper.domain.jdbcInterface.TgChatService;
import edu.java.scrapper.domain.model.Chat;
import edu.java.scrapper.domain.model.User;
import edu.java.scrapper.domain.repository.ChatRepository;
import edu.java.scrapper.domain.repository.UserRepository;
import edu.java.scrapper.exeption.ChatAlreadyRegisteredException;
import edu.java.scrapper.exeption.ChatNotFoundException;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JdbcTgChatService implements TgChatService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    @Autowired
    public JdbcTgChatService(ChatRepository chatRepository, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void register(long tgChatId) {
        Chat existingChat = chatRepository.findById(tgChatId);
        if (existingChat != null) {
            throw new ChatAlreadyRegisteredException("Chat already registered: " + tgChatId);
        }

        Chat chat = new Chat();
        chat.setId(tgChatId);
        chat.setCreatedAt(LocalDateTime.now());

        chatRepository.add(chat);

        User user = new User();
        user.setTelegramId(tgChatId);
        user.setUsername(null);
        user.setChatId(tgChatId);
        userRepository.add(user);
    }

    @Override
    @Transactional
    public void unregister(long tgChatId) {
        Chat chat = chatRepository.findById(tgChatId);
        if (chat == null) {
            throw new ChatNotFoundException("Chat not found: " + tgChatId);
        }

        userRepository.removeByChatId(tgChatId);
        chatRepository.remove(chat.getId());
    }
}
