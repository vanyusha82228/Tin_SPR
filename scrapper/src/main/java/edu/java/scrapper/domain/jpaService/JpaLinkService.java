package edu.java.scrapper.domain.jpaService;

import edu.java.scrapper.domain.jdbcInterface.LinkService;
import edu.java.scrapper.domain.jpaRepository.JpaLinkRepository;
import edu.java.scrapper.domain.jpaRepository.JpaUserLinkRepository;
import edu.java.scrapper.domain.jpaRepository.JpaUserRepository;
import edu.java.scrapper.domain.model.Link;
import edu.java.scrapper.domain.model.User;
import edu.java.scrapper.domain.model.UserLink;
import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;

public class JpaLinkService implements LinkService {
    private final JpaLinkRepository linkRepository;
    private final JpaUserLinkRepository userLinkRepository;
    private final JpaUserRepository userRepository;

    @Autowired
    public JpaLinkService(
        JpaLinkRepository linkRepository,
        JpaUserLinkRepository userLinkRepository,
        JpaUserRepository userRepository
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
        userLink.setLinkId(link);
        userLink.setUserId(user);

        userLinkRepository.save(userLink);

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

        userLinkRepository.deleteByUserAndLink(user, link);
        return null;
    }

    @Override
    public Collection<UserLink> listAll(long tgChatId) {
        User user = userRepository.findUserByChatId(tgChatId);
        if (user == null) {
            return Collections.emptyList();
        }
        return userLinkRepository.findByUser(user);
    }
}

