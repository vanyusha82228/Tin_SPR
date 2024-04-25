package edu.java.domain.jdbcInterface;

import edu.java.domain.model.Link;
import edu.java.domain.model.UserLink;
import java.net.URI;
import java.util.Collection;

public interface LinkService {
    void add(long tgChatId, URI url);

    void remove(long tgChatId, URI url);

    Collection<UserLink> listAll(long tgChatId);
}
