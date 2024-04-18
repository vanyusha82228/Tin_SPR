package edu.java.domain.jdbc;

import edu.java.domain.dao.LinkDao;
import edu.java.domain.jdbcInterface.LinkService;
import edu.java.domain.model.Link;
import java.net.URI;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JdbcLinkService implements LinkService {
    private final LinkDao linkDao;

    @Autowired
    public JdbcLinkService(LinkDao linkDao) {
        this.linkDao = linkDao;
    }

    @Override
    public Link add(long tgChatId, URI url) {
        Link link = new Link();
        link.setUri(url.toString());
        linkDao.add(link);
        return link;
    }

    @Override
    public Link remove(long tgChatId, URI url) {
        return null;
    }

    @Override
    public Collection<Link> listAll(long tgChatId) {
        return linkDao.findAll();
    }
}
