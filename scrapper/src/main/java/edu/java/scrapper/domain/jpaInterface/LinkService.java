package edu.java.scrapper.domain.jpaInterface;

import edu.java.scrapper.domain.modelJpa.UserLink;
import java.net.URI;
import java.util.Collection;

public interface LinkService {
    UserLink addUserLink(long tgChatId, URI url);

    UserLink removeUserLink(long tgChatId, URI url);

    Collection<UserLink> getAllUserLinks(long tgChatId);
}
