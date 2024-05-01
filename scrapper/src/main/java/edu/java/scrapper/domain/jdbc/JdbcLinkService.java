package edu.java.scrapper.domain.jdbc;

import edu.java.scrapper.domain.jdbcInterface.LinkService;
import edu.java.scrapper.domain.model.UserLink;
import edu.java.scrapper.domain.repository.LinkRepository;
import edu.java.scrapper.domain.repository.UserLinkRepository;
import edu.java.scrapper.domain.repository.UserRepository;
import java.net.URI;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;

public class JdbcLinkService implements LinkService {
    private final LinkRepository linkRepository;
    private final UserLinkRepository userLinkRepository;
    private final UserRepository userRepository;

    @Autowired
    public JdbcLinkService(
        LinkRepository linkRepository,
        UserLinkRepository userLinkRepository, UserRepository userRepository
    ) {
        this.linkRepository = linkRepository;
        this.userLinkRepository = userLinkRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserLink add(long tgChatId, URI url) {
        return userLinkRepository.addUserLink(tgChatId, url);
    }

    @Override
    public void remove(long tgChatId, URI url) {
        userLinkRepository.remove(tgChatId, url);
    }

    @Override
    public Collection<UserLink> listAll(long tgChatId) {
        return userLinkRepository.findAll();
    }
}
