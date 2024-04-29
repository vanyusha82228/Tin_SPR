package edu.java.domain.jdbcInterface;

import edu.java.domain.model.UserLink;
import java.net.URI;
import java.util.Collection;

public interface LinkService {
    UserLink add(long tgChatId, URI url);

    UserLink remove(long tgChatId, URI url);

    Collection<UserLink> listAll(long tgChatId);
}
