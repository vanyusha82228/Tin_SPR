package edu.java.scrapper.domain.jpaService;

import edu.java.scrapper.domain.jdbcInterface.LinkService;
import edu.java.scrapper.domain.jpaRepository.JpaUserLinkRepository;
import edu.java.scrapper.domain.jpaRepository.JpaUserRepository;
import edu.java.scrapper.domain.model.User;
import edu.java.scrapper.domain.model.UserLink;
import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class JpaLinkService implements LinkService {
    private final JpaUserLinkRepository userLinkRepository;
    private final JpaUserRepository userRepository;

    @Autowired
    public JpaLinkService(
        JpaUserLinkRepository userLinkRepository,
        JpaUserRepository userRepository
    ) {
        this.userLinkRepository = userLinkRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserLink add(long tgChatId, URI url) {
        return userLinkRepository.addByChatIdAndUrl(tgChatId, url);
    }

    @Override
    @Transactional
    public void remove(long tgChatId, URI url) {
        userLinkRepository.deleteByChatIdAndUrl(tgChatId, url);
    }

    @Override
    public Collection<UserLink> listAll(long tgChatId) {
        User user = userRepository.findUserByChatId(tgChatId);
        return user != null ? userLinkRepository.findByUser(user) : Collections.emptyList();
    }
}

