package edu.java.domain.jdbc;

import edu.java.domain.model.Chat;
import edu.java.domain.model.User;
import edu.java.domain.model.UserLink;
import edu.java.domain.repository.ChatRepository;
import edu.java.domain.repository.LinkRepository;
import edu.java.domain.jdbcInterface.LinkService;
import edu.java.domain.model.Link;
import java.net.URI;
import java.util.Collection;
import edu.java.domain.repository.UserLinkRepository;
import edu.java.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JdbcLinkService implements LinkService {
    private final LinkRepository linkRepository;
    private final ChatRepository chatRepository;
    private final UserLinkRepository userLinkRepository;
    private final UserRepository userRepository;

    @Autowired
    public JdbcLinkService(LinkRepository linkRepository, ChatRepository chatRepository,
                           UserLinkRepository userLinkRepository, UserRepository userRepository) {
        this.linkRepository = linkRepository;
        this.chatRepository = chatRepository;
        this.userLinkRepository = userLinkRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void add(long tgChatId, URI url) {
        Link link = linkRepository.findByUrl(url);
        User user = userRepository.findUserByChatId(tgChatId);
        UserLink userLink = new UserLink();
        userLink.setLinkId(link.getId());
        userLink.setUserId(user.getId());

        userLinkRepository.add(userLink);
    }

    @Override
    public void remove(long tgChatId, URI url) {
        Link link = linkRepository.findByUrl(url);
        User user = userRepository.findUserByChatId(tgChatId);

        userLinkRepository.remove(user, link);
    }

    @Override
    public Collection<UserLink> listAll(long tgChatId) {
        return userLinkRepository.findAll();
    }
}
