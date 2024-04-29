package edu.java.scrapper.domain.jdbc;

import edu.java.scrapper.domain.jdbcInterface.LinkService;
import edu.java.scrapper.domain.model.Link;
import edu.java.scrapper.domain.model.User;
import edu.java.scrapper.domain.model.UserLink;
import edu.java.scrapper.domain.repository.LinkRepository;
import edu.java.scrapper.domain.repository.UserLinkRepository;
import edu.java.scrapper.domain.repository.UserRepository;
import java.net.URI;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
        User user = userRepository.findUserByChatId(tgChatId);
        if (user == null) {
            return null;
        }
        Link link = linkRepository.findByUrl(url);
        if (link == null) {
            return null;
        }
        UserLink userLink = new UserLink();
        userLink.setLinkId(link.getId());
        userLink.setUserId(user.getId());

        userLinkRepository.add(userLink);

        return userLink;
    }

    @Override
    public UserLink remove(long tgChatId, URI url) {
        User user = userRepository.findUserByChatId(tgChatId);
        if (user == null) {
            return null;
        }
        Link link = linkRepository.findByUrl(url);
        if (link == null) {
            return null;
        }

        userLinkRepository.remove(user, link);
        return null;
    }

    @Override
    public Collection<UserLink> listAll(long tgChatId) {
        return userLinkRepository.findAll();
    }
}
