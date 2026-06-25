package edu.java.scrapper.domain.jdbc;

import edu.java.scrapper.domain.jdbcInterface.LinkService;
import edu.java.scrapper.domain.model.Link;
import edu.java.scrapper.domain.model.Resource;
import edu.java.scrapper.domain.model.User;
import edu.java.scrapper.domain.model.UserLink;
import edu.java.scrapper.domain.repository.LinkRepository;
import edu.java.scrapper.domain.repository.ResourceRepository;
import edu.java.scrapper.domain.repository.UserLinkRepository;
import edu.java.scrapper.domain.repository.UserRepository;
import edu.java.scrapper.exeption.ChatNotFoundException;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JdbcLinkService implements LinkService {
    private final LinkRepository linkRepository;
    private final UserLinkRepository userLinkRepository;
    private final UserRepository userRepository;
    private final ResourceRepository resourceRepository;

    @Autowired
    public JdbcLinkService(
        LinkRepository linkRepository,
        UserLinkRepository userLinkRepository,
        UserRepository userRepository,
        ResourceRepository resourceRepository
    ) {
        this.linkRepository = linkRepository;
        this.userLinkRepository = userLinkRepository;
        this.userRepository = userRepository;
        this.resourceRepository = resourceRepository;
    }

    @Override
    public Link add(long tgChatId, URI url) {
        User user = userRepository.findUserByChatId(tgChatId);
        if (user == null) {
            throw new ChatNotFoundException("Chat not found: " + tgChatId);
        }
        Link link = linkRepository.findByUrl(url);
        if (link == null) {
            Resource resource = resourceRepository.createIfAbsent(resolveResourceName(url));
            link = new Link();
            link.setUri(url.toString());
            link.setUpdatedAt(OffsetDateTime.now());
            link.setResourceId(resource.getId());
            link = linkRepository.create(link);
        }
        UserLink userLink = new UserLink();
        userLink.setLinkId(link.getId());
        userLink.setUserId(user.getId());

        userLinkRepository.add(userLink);

        return link;
    }

    private String resolveResourceName(URI url) {
        String host = url.getHost();
        if (host == null) {
            return "unknown";
        }
        if (host.equals("github.com") || host.endsWith(".github.com")) {
            return "github";
        }
        if (host.equals("stackoverflow.com") || host.endsWith(".stackoverflow.com")) {
            return "stackoverflow";
        }
        return "unknown";
    }

    @Override
    public Link remove(long tgChatId, URI url) {
        User user = userRepository.findUserByChatId(tgChatId);
        if (user == null) {
            throw new ChatNotFoundException("Chat not found: " + tgChatId);
        }
        Link link = linkRepository.findByUrl(url);
        if (link == null) {
            return null;
        }
        if (!userLinkRepository.existsByUserAndLink(user, link)) {
            return null;
        }

        userLinkRepository.remove(user, link);
        return link;
    }

    @Override
    public Collection<Link> listAll(long tgChatId) {
        User user = userRepository.findUserByChatId(tgChatId);
        if (user == null) {
            throw new ChatNotFoundException("Chat not found: " + tgChatId);
        }
        return userLinkRepository.findLinksByUser(user);
    }
}
