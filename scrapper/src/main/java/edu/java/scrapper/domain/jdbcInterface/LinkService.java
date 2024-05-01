package edu.java.scrapper.domain.jdbcInterface;


import edu.java.scrapper.domain.model.UserLink;
import java.net.URI;
import java.util.Collection;

public interface LinkService {
    UserLink add(long tgChatId, URI url);

    void remove(long tgChatId, URI url);

    Collection<UserLink> listAll(long tgChatId);
}
